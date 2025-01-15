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

    /**
     * 폴더 이름으로 엔티티 검색
     * @param folderName 검색할 폴더 이름
     * @return 해당 폴더 이름에 속한 CloudEntity 리스트
     */
    @Query("SELECT c FROM CloudEntity c WHERE c.folderName = :folderName")
    List<CloudEntity> findCloudEntitiesByFolderName(@Param("folderName") String folderName);

    /**
     * 휴지통에 있는 파일 조회
     * @param folderName 검색할 폴더 이름
     * @return 휴지통에 있는 CloudEntity 리스트
     */
    @Query("SELECT c FROM CloudEntity c WHERE c.isDeleted = true AND c.folderName = :folderName")
    List<CloudEntity> findDeletedFilesByFolderName(@Param("folderName") String folderName);

    /**
     * 1주일 이상 경과된 휴지통 파일 조회
     * @param expiredAt 경과 기준 날짜
     * @return 삭제된 지 1주일 이상 지난 CloudEntity 리스트
     */
    @Query("SELECT c FROM CloudEntity c WHERE c.isDeleted = true AND c.deletedAt <= :expiredAt")
    List<CloudEntity> findExpiredTrashFiles(@Param("expiredAt") LocalDateTime expiredAt);

    /**
     * 사용자 ID로 삭제된 파일 조회
     * @param userId 사용자 ID
     * @return 해당 사용자 ID에 속한 삭제된 CloudEntity 리스트
     */
    List<CloudEntity> findDeletedFilesByUserId(Long userId);
}
