package com.example.back.cloud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// DTO 클래스는 요청(Request) 또는 응답(Response)에 사용됩니다.
// 데이터를 명확히 구분하고 유효성을 검증합니다.
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class FavoritesDto {

    @NotNull(message = "ID는 필수 값입니다.")
    private Long id; // 즐겨찾기 항목의 고유 ID

    @NotNull(message = "파일 이름은 필수 값입니다.")
    @Size(min = 1, max = 255, message = "파일 이름은 1자 이상, 255자 이하로 입력해야 합니다.")
    private String filename; // 파일 이름

    @NotNull(message = "파일 크기는 필수 값입니다.")
    private long fileSize; // 파일 크기 (바이트 단위)
}
