package com.example.back.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.back.cloud.utils.FileCompressionUtil;
import com.example.back.cloud.dto.CloudDto;
import com.example.back.cloud.repository.CloudRepository;
import com.example.back.entity.CloudEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CloudService {

    private final FileStorageService fileStorageService;
    private final CloudRepository cloudRepository;

    @Autowired
    public CloudService(FileStorageService fileStorageService, CloudRepository cloudRepository) {
        this.fileStorageService = fileStorageService;
        this.cloudRepository = cloudRepository;
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

    // 폴더 내 파일 목록 조회 메서드
    public List<CloudDto> getFilesInFolder(String folderName) {
        List<CloudEntity> cloudEntities = cloudRepository.findCloudEntitiesByFolderName(folderName);
        return cloudEntities.stream()
                .map(entity -> new CloudDto(entity.getFilename(), entity.getFileSize(), "Available"))
                .collect(Collectors.toList());
    }

    // 파일을 휴지통으로 이동
    public void moveToTrash(Long fileId) {
        Optional<CloudEntity> fileOptional = cloudRepository.findById(fileId);
        if (fileOptional.isPresent()) {
            CloudEntity file = fileOptional.get();
            file.setDeleted(true);
            file.setDeletedAt(LocalDateTime.now());
            cloudRepository.save(file);
        } else {
            throw new RuntimeException("File not found.");
        }
    }

    // 휴지통 파일 목록 조회
    public List<CloudDto> getTrashFiles(Long userId) {
        List<CloudEntity> trashFiles = cloudRepository.findDeletedFilesByUserId(userId);
        return trashFiles.stream()
                .map(file -> new CloudDto(file.getFilename(), file.getFileSize(), "Deleted"))
                .collect(Collectors.toList());
    }

    // 휴지통 파일 영구 삭제
    public boolean permanentlyDeleteFile(Long fileId) {
        Optional<CloudEntity> fileOptional = cloudRepository.findById(fileId);
        if (fileOptional.isPresent()) {
            cloudRepository.deleteById(fileId);
            return true;
        }
        return false;
    }

    // 1주일 지난 휴지통 파일 자동 삭제
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정 실행
    public void deleteExpiredTrashFiles() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        List<CloudEntity> expiredFiles = cloudRepository.findExpiredTrashFiles(oneWeekAgo);
        cloudRepository.deleteAll(expiredFiles);
    }
}
