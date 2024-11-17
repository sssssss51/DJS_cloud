package com.example.back.cloud.service;

import org.springframework.stereotype.Service;

import java.io.File;
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
        Files.write(filePath, compressedData);
    }

    // 파일 읽기 메서드
    public byte[] retrieveFile(String filename) throws IOException {
        Path filePath = Paths.get(storageDirectory, filename);
        if (!Files.exists(filePath)) {
            throw new IOException("File not found: " + filename);
        }
        return Files.readAllBytes(filePath);
    }

    // 폴더 생성 및 파일 저장 메서드
    public void storeFileInFolder(String folderName, String originalFilename, byte[] data) throws IOException {
        Path folderPath = Paths.get(storageDirectory, folderName);
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }
        Path filePath = folderPath.resolve(originalFilename);
        Files.write(filePath, data);
    }

    // 특정 파일 삭제 메서드
    public boolean deleteFile(String folderName, String fileName) {
        Path filePath = Paths.get(storageDirectory, folderName, fileName);
        try {
            return Files.deleteIfExists(filePath); // 파일 삭제 성공 시 true 반환
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + filePath, e);
        }
    }

    // 폴더 및 모든 하위 파일 삭제 메서드
    public boolean deleteFolder(String folderName) {
        Path folderPath = Paths.get(storageDirectory, folderName);
        try {
            if (Files.exists(folderPath)) {
                Files.walk(folderPath) // 하위 파일과 폴더를 모두 순회
                        .sorted((a, b) -> b.compareTo(a)) // 하위 파일부터 삭제
                        .map(Path::toFile)
                        .forEach(File::delete); // 파일과 폴더 삭제
                return true; // 삭제 성공
            }
            return false; // 폴더가 없을 경우 false 반환
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete folder: " + folderPath, e);
        }
    }
}
