package com.example.back.user.repository;

import com.example.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일 존재 여부 확인
    boolean existsByUserEmail(String userEmail);

    // 이메일로 사용자 조회 (Optional 사용)
    Optional<User> findByUserEmail(String userEmail);

    // 삭제 상태와 삭제 요청 시간에 따라 사용자 조회
    List<User> findAllByIsDeletedTrueAndDeletedAtBefore(LocalDateTime dateTime);
}
