package com.example.back.cloud.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FolderFileUploadDTO {
    private String folderName; // 폴더 이름
    private MultipartFile file; // 업로드할 파일

    // 기본 생성자와 Lombok이 자동으로 생성하는 Getter/Setter 사용
}
