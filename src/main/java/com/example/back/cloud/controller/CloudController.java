package com.example.back.cloud.controller;

import com.example.back.cloud.dto.FolderFileUploadDTO;
import com.example.back.cloud.dto.FavoritesDto;
import com.example.back.cloud.service.CloudService;
import com.example.back.cloud.service.FavoritesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "폴더에 파일 업로드", description = "지정된 폴더에 파일을 업로드합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 업로드 성공"),
            @ApiResponse(responseCode = "500", description = "파일 업로드 실패")
    })
    @PostMapping("/uploadToFolder")
    public ResponseEntity<String> uploadFileToFolder(@ModelAttribute FolderFileUploadDTO folderFileUploadDTO) {
        try {
            String folderName = folderFileUploadDTO.getFolderName();
            MultipartFile file = folderFileUploadDTO.getFile();

            cloudService.uploadFileToFolder(folderName, file);
            return ResponseEntity.ok("File uploaded successfully to folder: " + folderName);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload file to folder: " + e.getMessage());
        }
    }

    @Operation(summary = "파일 다운로드", description = "지정된 폴더에서 파일을 다운로드합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 다운로드 성공"),
            @ApiResponse(responseCode = "500", description = "파일 다운로드 실패")
    })
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

    @Operation(summary = "파일 삭제", description = "지정된 폴더에서 파일을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "파일을 찾을 수 없음")
    })
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

    @Operation(summary = "폴더 삭제", description = "지정된 폴더와 하위 파일을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "폴더 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "폴더를 찾을 수 없음")
    })
    @DeleteMapping("/deleteFolder")
    public ResponseEntity<String> deleteFolder(@RequestParam("folderName") String folderName) {
        boolean isDeleted = cloudService.deleteFolder(folderName);
        if (isDeleted) {
            return ResponseEntity.ok("Folder deleted successfully: " + folderName);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Folder not found: " + folderName);
        }
    }

    @Operation(summary = "파일 즐겨찾기 추가", description = "파일을 즐겨찾기에 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "즐겨찾기 추가 성공")
    })
    @PostMapping("/favorites")
    public String addFavorite(@RequestParam Long userId, @RequestParam Long fileId) {
        return favoritesService.addFavorite(userId, fileId);
    }

    @Operation(summary = "즐겨찾기 목록 조회", description = "사용자의 즐겨찾기 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "즐겨찾기 조회 성공")
    })
    @GetMapping("/favorites")
    public List<FavoritesDto> getFavorites(@RequestParam Long userId) {
        return favoritesService.getFavoritesByUserId(userId);
    }
}
