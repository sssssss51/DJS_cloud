package com.example.back.cloud.controller;

import com.example.back.cloud.service.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // 파일 업로드 엔드포인트
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            cloudService.uploadAndCompressFile(file);
            return ResponseEntity.ok("File uploaded and compressed successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload and compress file: " + e.getMessage());
        }
    }

    // 파일 다운로드 엔드포인트
    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) {
        try {
            byte[] fileData = cloudService.downloadAndDecompressFile(filename);
            return ResponseEntity.ok(fileData);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
