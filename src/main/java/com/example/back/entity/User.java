package com.example.back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
// 'users' 테이블은 예약어 충돌을 방지하기 위해 명시적으로 이름을 설정합니다.
@Table(name = "users") // 테이블 이름을 변경하여 예약어 충돌 방지
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키

    private String userName; // 이름
    private String userPassword; // 비밀번호
    // 이메일은 사용자 식별자로 사용됩니다.
    @Column(unique = true, nullable = false) // 이메일은 고유하고 반드시 입력되어야 합니다.
    private String userEmail; // 이메일 (식별자)

    // 삭제 상태를 나타내는 필드 추가
    private boolean isDeleted = false; // 삭제 상태 (기본값: false)

    // 삭제 예정 시간을 기록하는 필드 추가
    private LocalDateTime deletedAt; // 삭제 요청 시간
}
