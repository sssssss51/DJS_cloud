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

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long fileId; // 파일 ID 참조

    @ManyToOne
    @JoinColumn(name = "fileId", insertable = false, updatable = false)
    private CloudEntity file;

}