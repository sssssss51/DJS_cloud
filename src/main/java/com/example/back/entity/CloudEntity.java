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
    // 파일 삭제 여부를 나타내는 플래그입니다. 기본값은 false입니다.
    private boolean isDeleted = false; // 삭제 여부

    // 파일이 삭제된 시간을 기록하는 필드입니다.
    // 삭제 시간이 null이 아닌 경우, 파일은 삭제된 상태로 간주됩니다.
    private LocalDateTime deletedAt; // 삭제된 시간
}
