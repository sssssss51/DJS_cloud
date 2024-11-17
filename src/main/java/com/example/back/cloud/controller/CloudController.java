package com.example.back.cloud.controller;

import com.example.back.cloud.dto.FolderFileUploadDTO;
import com.example.back.cloud.service.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/cloud")
public class CloudController {

    private final CloudService cloudService;

    @Autowired
    public CloudController(CloudService cloudService) {
        this.cloudService = cloudService;
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

    // 폴더 내 파일 다운로드 엔드포인트 (변경 없음)
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
}
