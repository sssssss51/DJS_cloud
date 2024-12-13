package com.example.back.cloud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FavoritesDto {

    private Long id;
    private String filename;
    private long fileSize;
}
