package com.example.back.cloud.repository;

import com.example.back.entity.CloudEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CloudRepository extends JpaRepository<CloudEntity, Long> {
    // 필요에 따라 사용자 정의 쿼리 메서드를 추가하세요
}
