package com.example.back.user.dto;

import lombok.Getter;
import lombok.Setter;

// DTO 클래스는 계층 간 데이터 전달을 담당하며, 입력 데이터에 유효성 검사를 적용합니다.
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class UserProfileDTO {

    @NotNull(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String userEmail; // 사용자 이메일

    @NotNull(message = "이름은 필수 입력 값입니다.")
    @Size(min = 2, max = 50, message = "이름은 최소 2자에서 최대 50자까지 입력 가능합니다.")
    private String userName; // 사용자 이름

    @NotNull(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String userPassword; // 사용자 비밀번호
}

