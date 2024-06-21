package co.istad.elearningapi.controller;

import co.istad.elearningapi.dto.FileDto;
import co.istad.elearningapi.service.FileUploadService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@SecurityRequirement(name="bearerAuth")
public class FileUploadController {
    private final FileUploadService fileUploadService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/single", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    FileDto uploadFileSingle(@RequestPart MultipartFile file) {
//        System.out.println(file.getOriginalFilename());
//        System.out.println(file.getInputStream());
        return fileUploadService.uploadFileSingle(file);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<FileDto> uploadMultipleFile(@RequestPart List<MultipartFile> files) {
        System.out.println("Checking: " + files);
        System.out.println("Checking Type: " + files.getClass());
        return fileUploadService.uploadFileMultiple(files);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{name}")
    FileDto findByName(@PathVariable String name) throws IOException {
        return fileUploadService.findByName(name);
    }
    @GetMapping
    List<FileDto> findList() {
        return fileUploadService.findList();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping("/{name}")
    void deleteByName(@PathVariable String name) {
        fileUploadService.deleteByName(name);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping
    void deleteALl() {
        fileUploadService.deleteAll();
    }
    @GetMapping("/{name}/download")
    void downloadFileByName(HttpServletRequest request, HttpServletResponse response, @PathVariable String name) throws IOException {
        fileUploadService.downloadByName(name, request, response);
    }
}
