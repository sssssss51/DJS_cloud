package com.example.back.cloud.repository;

import com.example.back.entity.FavoritesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritesRepository extends JpaRepository<FavoritesEntity, Long> {

    /**
     * 특정 사용자의 즐겨찾기 항목을 조회합니다.
     *
     * @param userId 사용자 ID
     * @return 해당 사용자의 즐겨찾기 항목 리스트
     */
    @Query("SELECT f FROM FavoritesEntity f WHERE f.user.id = :userId")
    List<FavoritesEntity> findByUserId(@Param("userId") Long userId);
}
