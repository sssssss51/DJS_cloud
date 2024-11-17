package com.example.back.cloud.service;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final String storageDirectory = "./uploaded_files";

    public FileStorageService() {
        // 서비스 초기화 시 디렉토리 존재 여부 확인 및 생성
        File directory = new File(storageDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    // 기본 저장 경로 반환 메서드
    public String getStorageDirectory() {
        return storageDirectory;
    }

    // 파일 저장 메서드
    public void storeFile(String originalFilename, byte[] compressedData) throws IOException {
        Path filePath = Paths.get(storageDirectory, originalFilename);
        try (FileOutputStream outputStream = new FileOutputStream(filePath.toFile())) {
            outputStream.write(compressedData);
        }
    }

    // 파일 읽기 메서드
    public byte[] retrieveFile(String filename) throws IOException {
        Path path = Paths.get(storageDirectory, filename);
        return Files.readAllBytes(path);
    }

    // 폴더를 생성하고 파일 저장 메서드
    public void storeFileInFolder(String folderName, String originalFilename, byte[] data) throws IOException {
        // 폴더 경로 설정
        Path folderPath = Paths.get(storageDirectory, folderName);
        // 폴더가 없으면 생성
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }
        // 파일 저장 경로 설정
        Path filePath = folderPath.resolve(originalFilename);
        try (FileOutputStream outputStream = new FileOutputStream(filePath.toFile())) {
            outputStream.write(data);
        }
    }
}
