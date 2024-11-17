package com.example.back.cloud.repository;

import com.example.back.entity.CloudEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CloudRepository extends JpaRepository<CloudEntity, Long> {

    // 폴더 이름으로 엔티티 검색
    @Query("SELECT c FROM CloudEntity c WHERE c.folderName = :folderName")
    List<CloudEntity> findCloudEntitiesByFolderName(@Param("folderName") String folderName);
}
