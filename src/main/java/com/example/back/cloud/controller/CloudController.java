package com.example.back.cloud.controller;

import com.example.back.cloud.dto.FolderFileUploadDTO;
import com.example.back.cloud.dto.FavoritesDto;
import com.example.back.cloud.dto.CloudDto;
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
// 클라우드 파일 관련 요청을 처리하는 컨트롤러 클래스입니다.
public class CloudController {

    private final CloudService cloudService;
    private final FavoritesService favoritesService;

    @Autowired
    public CloudController(CloudService cloudService, FavoritesService favoritesService) {
        this.cloudService = cloudService;
        this.favoritesService = favoritesService;
    }

    // 휴지통 파일 조회
    @Operation(summary = "휴지통 파일 조회", description = "사용자의 휴지통에 있는 파일 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "휴지통 조회 성공"),
            @ApiResponse(responseCode = "404", description = "휴지통이 비어있음")
    })
    @GetMapping("/trash")
    public ResponseEntity<List<CloudDto>> getTrashFiles(@RequestParam Long userId) {
        List<CloudDto> trashFiles = cloudService.getTrashFiles(userId);
        if (trashFiles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(trashFiles);
    }

    // 휴지통 파일 영구 삭제
    @Operation(summary = "휴지통 파일 영구 삭제", description = "휴지통에서 파일을 영구적으로 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 영구 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "파일을 찾을 수 없음")
    })
    @DeleteMapping("/trash/{fileId}")
    public ResponseEntity<String> permanentlyDeleteFile(@PathVariable Long fileId) {
        boolean isDeleted = cloudService.permanentlyDeleteFile(fileId);
        if (isDeleted) {
            return ResponseEntity.ok("File permanently deleted.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found in trash.");
    }

    // 추가 예외 처리 메서드
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + ex.getMessage());
    }
}
