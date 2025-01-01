package com.example.back.cloud.repository;

import com.example.back.entity.CloudEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CloudRepository extends JpaRepository<CloudEntity, Long> {

    // 폴더 이름으로 엔티티 검색
    @Query("SELECT c FROM CloudEntity c WHERE c.folderName = :folderName")
    List<CloudEntity> findCloudEntitiesByFolderName(@Param("folderName") String folderName);

    // 휴지통에 있는 파일 조회
    @Query("SELECT c FROM CloudEntity c WHERE c.isDeleted = true AND c.folderName = :folderName")
    List<CloudEntity> findDeletedFilesByFolderName(@Param("folderName") String folderName);

    // 1주일 이상 경과된 휴지통 파일 조회
    @Query("SELECT c FROM CloudEntity c WHERE c.isDeleted = true AND c.deletedAt <= :expiredAt")
    List<CloudEntity> findExpiredTrashFiles(@Param("expiredAt") LocalDateTime expiredAt);

    List<CloudEntity> findDeletedFilesByUserId(Long userId);
}
