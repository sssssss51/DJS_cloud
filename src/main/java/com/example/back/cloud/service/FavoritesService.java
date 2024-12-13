package com.example.back.cloud.service;

import com.example.back.cloud.dto.FavoritesDto;
import com.example.back.entity.FavoritesEntity;
import com.example.back.cloud.repository.FavoritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;

    @Autowired
    public FavoritesService(FavoritesRepository favoritesRepository) {
        this.favoritesRepository = favoritesRepository;
    }

    public String addFavorite(Long userId, Long fileId) {
        FavoritesEntity favorite = new FavoritesEntity();
        favorite.setUserId(userId);
        favorite.setFileId(fileId);
        favoritesRepository.save(favorite);
        return "파일이 즐겨찾기에 추가되었습니다.";
    }

    public List<FavoritesDto> getFavoritesByUserId(Long userId) {
        List<FavoritesEntity> favorites = favoritesRepository.findByUserId(userId);
        return favorites.stream()
                .map(f -> new FavoritesDto(f.getId(), f.getFile().getFilename(), f.getFile().getFileSize()))
                .collect(Collectors.toList());
    }
}

