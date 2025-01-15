package com.example.back.cloud.dto;

import lombok.Getter;
import lombok.Setter;

// DTO 클래스는 요청(Request) 또는 응답(Response)에 사용됩니다.
// 데이터를 명확히 구분하고 유효성을 검증합니다.
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class FolderFileUploadDTO {

    @NotNull(message = "폴더 이름은 필수 입력 값입니다.")
    @Size(min = 1, max = 255, message = "폴더 이름은 1자 이상, 255자 이하로 입력해야 합니다.")
    private String folderName; // 폴더 이름

    @NotNull(message = "파일 이름은 필수 입력 값입니다.")
    @Size(min = 1, max = 255, message = "파일 이름은 1자 이상, 255자 이하로 입력해야 합니다.")
    private String filename; // 파일 이름

    @NotNull(message = "파일 크기는 필수 입력 값입니다.")
    private long fileSize; // 파일 크기 (바이트 단위)

    // 기본 생성자
    public FolderFileUploadDTO() {
    }

    // 모든 필드를 포함한 생성자
    public FolderFileUploadDTO(String folderName, String filename, long fileSize) {
        this.folderName = folderName;
        this.filename = filename;
        this.fileSize = fileSize;
    }

    // 디버깅을 위한 toString 메서드
    @Override
    public String toString() {
        return "FolderFileUploadDTO{" +
                "folderName='" + folderName + '\'' +
                ", filename='" + filename + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}
