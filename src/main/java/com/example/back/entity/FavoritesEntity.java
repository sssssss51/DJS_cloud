package com.example.back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FavoritesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 정보와 다대일 관계를 정의합니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false) // 외래 키 userId를 참조
    private User user;

    // 파일 정보와 다대일 관계를 정의합니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fileId", nullable = false) // 외래 키 fileId를 참조
    private CloudEntity file;

}
