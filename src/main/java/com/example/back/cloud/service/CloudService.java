package com.example.back.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.back.cloud.utils.FileCompressionUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CloudService {

    private final FileStorageService fileStorageService;

    @Autowired
    public CloudService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    // 파일 업로드 및 압축 처리 메서드
    public void uploadAndCompressFile(MultipartFile file) throws IOException {
        byte[] compressedData = FileCompressionUtil.compress(file.getBytes(), file.getOriginalFilename());
        fileStorageService.storeFile(file.getOriginalFilename(), compressedData);
    }

    // 파일 다운로드 및 압축 해제 메서드
    public byte[] downloadAndDecompressFile(String filename) throws IOException {
        byte[] compressedData = fileStorageService.retrieveFile(filename);
        return FileCompressionUtil.decompress(compressedData);
    }

    // 폴더 지정 파일 업로드 메서드
    public void uploadFileToFolder(String folderName, MultipartFile file) throws IOException {
        byte[] compressedData = FileCompressionUtil.compress(file.getBytes(), file.getOriginalFilename());
        fileStorageService.storeFileInFolder(folderName, file.getOriginalFilename(), compressedData);
    }

    // 폴더 내 파일 다운로드 메서드
    public byte[] downloadFileFromFolder(String folderName, String fileName) throws IOException {
        Path filePath = Paths.get(fileStorageService.getStorageDirectory(), folderName, fileName);
        if (!Files.exists(filePath)) {
            throw new IOException("File not found in folder: " + folderName + "/" + fileName);
        }
        return Files.readAllBytes(filePath);
    }

    // 특정 파일 삭제 메서드
    public boolean deleteFile(String folderName, String fileName) {
        return fileStorageService.deleteFile(folderName, fileName);
    }

    // 특정 폴더 삭제 메서드
    public boolean deleteFolder(String folderName) {
        return fileStorageService.deleteFolder(folderName);
    }
}
