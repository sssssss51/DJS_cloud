package com.example.back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users") // 예약어 충돌 방지를 위해 테이블 이름 명시
@Getter
@Setter
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드를 포함한 생성자 추가
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성되는 기본 키
    private Long id;

    @Column(nullable = false, length = 50) // 이름 필드 제한 설정
    private String userName;

    @Column(nullable = false) // 비밀번호는 반드시 입력되어야 함
    private String userPassword;

    @Column(unique = true, nullable = false, length = 100) // 이메일은 고유하고 최대 길이 설정
    private String userEmail;

    @Column(nullable = false) // 삭제 상태 (기본값: false)
    private boolean isDeleted = false;

    private LocalDateTime deletedAt; // 삭제 요청 시간
}
