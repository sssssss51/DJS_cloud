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
        // 파일 데이터를 압축
        byte[] compressedData = FileCompressionUtil.compress(file.getBytes(), file.getOriginalFilename());
        // 압축된 파일 저장
        fileStorageService.storeFile(file.getOriginalFilename(), compressedData);
    }

    // 파일 다운로드 및 압축 해제 메서드
    public byte[] downloadAndDecompressFile(String filename) throws IOException {
        // 압축된 파일 데이터를 가져옴
        byte[] compressedData = fileStorageService.retrieveFile(filename);
        // 파일 데이터 압축 해제
        return FileCompressionUtil.decompress(compressedData);
    }

    // 폴더 지정 파일 업로드 메서드
    public void uploadFileToFolder(String folderName, MultipartFile file) throws IOException {
        // 파일 데이터를 압축
        byte[] compressedData = FileCompressionUtil.compress(file.getBytes(), file.getOriginalFilename());
        // 지정된 폴더에 압축된 파일 저장
        fileStorageService.storeFileInFolder(folderName, file.getOriginalFilename(), compressedData);
    }

    // 폴더 내 파일 다운로드 메서드
    public byte[] downloadFileFromFolder(String folderName, String fileName) throws IOException {
        // 폴더와 파일 경로 설정
        Path filePath = Paths.get(fileStorageService.getStorageDirectory(), folderName, fileName);
        // 파일 존재 여부 확인
        if (!Files.exists(filePath)) {
            throw new IOException("File not found in folder: " + folderName + "/" + fileName);
        }
        // 파일 읽기 및 반환
        return Files.readAllBytes(filePath);
    }
}
