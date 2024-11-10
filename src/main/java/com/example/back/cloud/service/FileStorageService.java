package com.example.back.cloud.service;

import org.springframework.stereotype.Service;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final String storageDirectory = "./uploaded_files";

    // 파일 저장 메서드
    public void storeFile(String originalFilename, byte[] compressedData) throws IOException {
        Path filePath = Paths.get(storageDirectory, originalFilename);
        try (FileOutputStream outputStream = new FileOutputStream(filePath.toFile())) {
            outputStream.write(compressedData);
        }
    }

    // 파일 읽기 메서드 (예시로 포함)
    public byte[] retrieveFile(String filename) throws IOException {
        Path path = Paths.get(storageDirectory, filename);
        return java.nio.file.Files.readAllBytes(path);
    }
}
