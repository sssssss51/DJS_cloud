package com.example.back.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
    private String userName;      // 사용자 이름
    private String userPassword;  // 사용자 비밀번호
    private String userEmail;     // 사용자 이메일 (수정 가능하도록 추가)
}
