package com.example.web_shop_ptit.admin.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Service
public class FilesStorageService {
    private final ResourceLoader resourceLoader;
    private final String uploadRoot = "public";

    public FilesStorageService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String saveWithNewName(MultipartFile file, String newFileName) {
        try {
            LocalDate currentDate = LocalDate.now();
            String year = String.valueOf(currentDate.getYear());
            String month = String.format("%02d", currentDate.getMonthValue());
            String day = String.format("%02d", currentDate.getDayOfMonth());

            String rootPath = Paths.get("src/main/resources/static/public/products", year, month, day).toString();
            Path uploadPath = Paths.get(rootPath);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(file.getInputStream(), filePath);

            return "/public/products/" + year + "/" + month + "/" + day + "/" + newFileName;
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }


    public void deleteFile(String filePath) {
        try {
            if (isValidPath(filePath)) {
                Path fileToDelete = Paths.get(filePath);
                Files.deleteIfExists(fileToDelete);
            }
        } catch (IOException e) {
            System.err.println("Unable to delete the file: " + e.getMessage());
        }
    }

    private boolean isValidPath(String filePath) {
        try {
            // Kiểm tra tính hợp lệ của đường dẫn bằng cách tạo đối tượng Path
            Paths.get(filePath);
            return true;
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
    }

}
