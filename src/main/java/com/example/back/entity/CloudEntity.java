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
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private long fileSize;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String folderName;

    @Column(nullable = false)
    private Long userId; // 사용자 ID 추가

    @Column(nullable = false)
    private boolean isDeleted = false; // 삭제 여부

    private LocalDateTime deletedAt; // 삭제된 시간
}
