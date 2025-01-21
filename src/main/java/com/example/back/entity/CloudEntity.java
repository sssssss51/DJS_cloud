package com.example.back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class CloudEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 식별자

    @Column(nullable = false)
    private String filename; // 파일 이름

    @Column(nullable = false)
    private long fileSize; // 파일 크기

    @Column(nullable = false)
    private String filePath; // 파일 경로

    @Column(nullable = false)
    private String folderName; // 폴더 이름

    @Column(nullable = false)
    private Long userId; // 사용자 ID

    @Column(nullable = false)
    private boolean isDeleted = false; // 삭제 여부, 기본값 false

    private LocalDateTime deletedAt; // 삭제된 시간 (null이면 삭제되지 않은 상태)

    /**
     * 파일 삭제 상태를 설정하고 삭제 시간을 기록하는 메서드입니다.
     */
    public void markAsDeleted() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    /**
     * 파일 복원 상태를 설정하고 삭제 시간을 초기화하는 메서드입니다.
     */
    public void restore() {
        this.isDeleted = false;
        this.deletedAt = null;
    }
}
