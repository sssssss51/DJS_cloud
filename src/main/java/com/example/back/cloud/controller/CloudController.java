package com.example.back.cloud.controller;

import com.example.back.cloud.dto.FolderFileUploadDTO;
import com.example.back.cloud.dto.FavoritesDto;
import com.example.back.cloud.service.CloudService;
import com.example.back.cloud.service.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cloud")
public class CloudController {

    private final CloudService cloudService;
    private final FavoritesService favoritesService;

    @Autowired
    public CloudController(CloudService cloudService, FavoritesService favoritesService) {
        this.cloudService = cloudService;
        this.favoritesService = favoritesService;
    }

    // 폴더 지정 파일 업로드 엔드포인트 (DTO 사용)
    @PostMapping("/uploadToFolder")
    public ResponseEntity<String> uploadFileToFolder(@ModelAttribute FolderFileUploadDTO folderFileUploadDTO) {
        try {
            // DTO에서 데이터 가져오기
            String folderName = folderFileUploadDTO.getFolderName();
            MultipartFile file = folderFileUploadDTO.getFile();

            // 서비스 호출
            cloudService.uploadFileToFolder(folderName, file);
            return ResponseEntity.ok("File uploaded successfully to folder: " + folderName);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload file to folder: " + e.getMessage());
        }
    }

    // 폴더 내 파일 다운로드 엔드포인트
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(
            @RequestParam("folderName") String folderName,
            @RequestParam("fileName") String fileName) {
        try {
            byte[] fileData = cloudService.downloadFileFromFolder(folderName, fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            return ResponseEntity.ok().headers(headers).body(fileData);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // 폴더 내 파일 삭제 엔드포인트
    @DeleteMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(
            @RequestParam("folderName") String folderName,
            @RequestParam("fileName") String fileName) {
        boolean isDeleted = cloudService.deleteFile(folderName, fileName);
        if (isDeleted) {
            return ResponseEntity.ok("File deleted successfully: " + fileName);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: " + fileName);
        }
    }

    // 폴더 삭제 엔드포인트
    @DeleteMapping("/deleteFolder")
    public ResponseEntity<String> deleteFolder(@RequestParam("folderName") String folderName) {
        boolean isDeleted = cloudService.deleteFolder(folderName);
        if (isDeleted) {
            return ResponseEntity.ok("Folder deleted successfully: " + folderName);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Folder not found: " + folderName);
        }
    }

    // 파일 즐겨찾기 추가
    @PostMapping("/favorites")
    public String addFavorite(@RequestParam Long userId, @RequestParam Long fileId) {
        return favoritesService.addFavorite(userId, fileId);
    }

    // 사용자 즐겨찾기 파일 조회
    @GetMapping("/favorites")
    public List<FavoritesDto> getFavorites(@RequestParam Long userId) {
        return favoritesService.getFavoritesByUserId(userId);
    }
}
