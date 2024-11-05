package com.example.back.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDTO {
    private String userName; //이름
    private String userId; //아이디
    private String userPassword; // 비밀번호
    private String userEmail; //이메일
}
