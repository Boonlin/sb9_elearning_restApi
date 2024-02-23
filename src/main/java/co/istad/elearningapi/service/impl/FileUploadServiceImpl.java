package co.istad.elearningapi.service.impl;

import co.istad.elearningapi.dto.FileDto;
import co.istad.elearningapi.service.FileUploadService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    private ResourceLoader resourceLoader;
    @Value("${file-upload.server-path}")
    private String serverPath;

    @Value("${file-upload.base-uri}")
    private String baseUri;


    @Autowired
    public FileUploadServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public FileDto uploadFileSingle(MultipartFile file) {
        // extract file extension
        // get last index of .
        int lastIndexOfDot = file.getOriginalFilename().lastIndexOf(".");
        String extension = file.getOriginalFilename().substring(lastIndexOfDot + 1);
        // Create new unique file name
        String newFileName = UUID.randomUUID() + "." + extension;

        System.out.println(file.getOriginalFilename());
        System.out.println(newFileName);
        System.out.println(file.getContentType());
        System.out.println(extension);

        String absolutePath = serverPath + newFileName;
        Path path = Paths.get(absolutePath);
        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return FileDto.builder().name(newFileName).extension(extension).size(file.getSize()).uri(baseUri + newFileName).build();
    }


    @Override
    public List<FileDto> uploadFileMultiple(List<MultipartFile> files) {
        List<FileDto> fileDtoList = new ArrayList<>();
        files.forEach(file -> {
            fileDtoList.add(this.uploadFileSingle(file));
        });
        return fileDtoList;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public FileDto findByName(String name) throws IOException {
        Path path = Paths.get(serverPath + name);
        Resource res = UrlResource.from(path.toUri());
        System.out.println("name:" + name);
        System.out.println("serverPath:" + serverPath);
        System.out.println("res:" + res);
        return FileDto.builder().name(res.getFilename()).size(res.contentLength()).extension(this.extractExtension(res.getFilename())).uri(baseUri + res.getFilename()).build();
    }

    private String extractExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf(".");
        return fileName.substring(lastIndexOfDot + 1);
    }


    @Override
    public void deleteByName(String name) {
        try {
            Resource resource = resourceLoader.getResource("file:" + serverPath);
            File directory = resource.getFile();
            if (directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile() && file.getName().equals(name)) {
                            if (file.delete()) {
                                System.out.println("File deleted successfully: " + file.getName());
                            } else {
                                System.out.println("Failed to delete the file: " + file.getName());
                            }
                            return;
                        }
                    }
                }
            }
            System.out.println("File not found: " + name);
        } catch (IOException e) {
            // Handle exception
            e.printStackTrace();
        }

    }

    @Override
    public void deleteAll() {
        try {
            Resource resource = resourceLoader.getResource("file:" + serverPath);
            File directory = resource.getFile();
            if (directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            file.delete(); // Delete the file
                        }
                    }
                }
            }
        } catch (IOException e) {
            // Handle exception
            e.printStackTrace();
        }
        findList();
    }

    @Override
    public List<FileDto> findList() {
        List<FileDto> fileList = new ArrayList<>();
        try {
            Resource resource = resourceLoader.getResource("file:" + serverPath);
            File directory = resource.getFile();
            System.out.println("directory:" + directory.listFiles().length);
            if (directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            fileList.add(buildFileDto(file));
                        }
                    }
                }
            }
        } catch (IOException e) {
            // Handle exception
            e.printStackTrace();
        }
        System.out.println("listFile" + fileList);
        return fileList;
    }

    private FileDto buildFileDto(File file) {
        return FileDto.builder().name(file.getName()).size(file.length()).extension(extractExtension(file.getName())).uri(baseUri + file.getName()).build();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public void downloadByName(String name, HttpServletRequest request, HttpServletResponse response) {
        try {
            // Load the file resource
            Resource resource = resourceLoader.getResource("file:" + serverPath + name);

            // If the resource exists and is readable
            if (resource.exists() && resource.isReadable()) {
                // Set content type
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

                // Set content disposition header
                String headerValue = String.format("attachment; filename=\"%s\"", name);
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, headerValue);

                // Copy resource content to response output stream
                try (InputStream inputStream = resource.getInputStream(); OutputStream outputStream = response.getOutputStream()) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                // If resource doesn't exist or is not readable, return 404 Not Found
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}


