package co.istad.elearningapi.service;

import co.istad.elearningapi.dto.FileDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileUploadService {

    FileDto findByName(String name) throws IOException;

    FileDto uploadFileSingle(MultipartFile file);

    List<FileDto> uploadFileMultiple(List<MultipartFile> files);

    void deleteByName(String name);

    void deleteAll();

    List<FileDto> findList();

    void downloadByName(String name, HttpServletRequest request, HttpServletResponse response);
}
