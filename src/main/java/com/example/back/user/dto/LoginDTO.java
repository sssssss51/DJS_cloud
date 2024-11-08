package com.example.back.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String userPassword; // 비밀번호
    private String userEmail; //이메일
}
