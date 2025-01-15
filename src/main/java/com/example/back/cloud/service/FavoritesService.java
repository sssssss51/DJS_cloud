package com.example.back.cloud.service;

import com.example.back.cloud.dto.FavoritesDto;
import com.example.back.entity.FavoritesEntity;
import com.example.back.entity.User;
import com.example.back.entity.CloudEntity;
import com.example.back.cloud.repository.FavoritesRepository;
import com.example.back.user.repository.UserRepository;
import com.example.back.cloud.repository.CloudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final UserRepository userRepository;
    private final CloudRepository cloudRepository;

    @Autowired
    public FavoritesService(FavoritesRepository favoritesRepository, UserRepository userRepository, CloudRepository cloudRepository) {
        this.favoritesRepository = favoritesRepository;
        this.userRepository = userRepository;
        this.cloudRepository = cloudRepository;
    }

    @Transactional
    public String addFavorite(Long userId, Long fileId) {
        // 사용자 및 파일 엔티티를 데이터베이스에서 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. ID: " + userId));
        CloudEntity file = cloudRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("파일을 찾을 수 없습니다. ID: " + fileId));

        // 즐겨찾기 엔티티 생성 및 저장
        FavoritesEntity favorite = new FavoritesEntity();
        favorite.setUser(user);
        favorite.setFile(file);
        favoritesRepository.save(favorite);

        return "파일이 즐겨찾기에 추가되었습니다.";
    }

    @Transactional(readOnly = true)
    public List<FavoritesDto> getFavoritesByUserId(Long userId) {
        // 특정 사용자의 즐겨찾기 엔티티를 조회
        List<FavoritesEntity> favorites = favoritesRepository.findByUserId(userId);

        // 즐겨찾기 엔티티를 DTO로 변환하여 반환
        return favorites.stream()
                .map(f -> new FavoritesDto(
                        f.getId(),
                        f.getFile().getFilename(),
                        f.getFile().getFileSize()
                ))
                .collect(Collectors.toList());
    }
}
