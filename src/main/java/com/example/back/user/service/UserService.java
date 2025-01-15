// 수정 및 추가된 주석이 포함된 버전입니다.

package com.example.back.user.service;

import com.example.back.entity.User;
import com.example.back.user.dto.LoginDTO;
import com.example.back.user.dto.SignupDTO;
import com.example.back.user.dto.UserProfileDTO;
import com.example.back.user.dto.UserUpdateDTO;
import com.example.back.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입
    public String signup(SignupDTO signupDTO) {
        logger.info("회원가입 요청 처리 시작: {}", signupDTO.getUserEmail());

        // 이메일 중복 체크
        if (userRepository.existsByUserEmail(signupDTO.getUserEmail())) {
            logger.warn("회원가입 실패: 이미 존재하는 이메일 - {}", signupDTO.getUserEmail());
            return "이미 존재하는 이메일입니다.";
        }

        // 새로운 사용자 생성
        String encodedPassword = passwordEncoder.encode(signupDTO.getUserPassword());
        User newUser = new User();
        newUser.setUserName(signupDTO.getUserName());
        newUser.setUserPassword(encodedPassword);
        newUser.setUserEmail(signupDTO.getUserEmail());

        userRepository.save(newUser);
        logger.info("회원가입 성공: {}", signupDTO.getUserEmail());
        return "회원가입이 성공적으로 완료되었습니다.";
    }

    // 로그인
    public String login(LoginDTO loginDTO) {
        logger.info("로그인 요청 처리 시작: {}", loginDTO.getUserEmail());

        Optional<User> optionalUser = userRepository.findByUserEmail(loginDTO.getUserEmail());
        if (optionalUser.isEmpty()) {
            logger.warn("로그인 실패: 이메일 존재하지 않음 - {}", loginDTO.getUserEmail());
            return "이메일이 존재하지 않습니다.";
        }

        User user = optionalUser.get();
        if (passwordEncoder.matches(loginDTO.getUserPassword(), user.getUserPassword())) {
            logger.info("로그인 성공: {}", loginDTO.getUserEmail());
            return "로그인 성공";
        } else {
            logger.warn("로그인 실패: 이메일 또는 비밀번호 불일치 - {}", loginDTO.getUserEmail());
            return "이메일 또는 비밀번호가 잘못되었습니다.";
        }
    }

    // 회원정보 조회
    public UserProfileDTO getProfile(String userEmail) {
        logger.info("회원정보 조회 요청: {}", userEmail);

        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> {
                    logger.warn("회원정보 조회 실패: 사용자 없음 - {}", userEmail);
                    return new RuntimeException("사용자를 찾을 수 없습니다.");
                });

        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUserEmail(user.getUserEmail());
        userProfileDTO.setUserName(user.getUserName());
        userProfileDTO.setUserPassword(user.getUserPassword());

        logger.info("회원정보 조회 성공: {}", userEmail);
        return userProfileDTO;
    }

    // 회원정보 수정
    public String updateProfile(UserUpdateDTO userUpdateDTO) {
        logger.info("회원정보 수정 요청: {}", userUpdateDTO.getUserEmail());

        User user = userRepository.findByUserEmail(userUpdateDTO.getUserEmail())
                .orElseThrow(() -> {
                    logger.warn("회원정보 수정 실패: 사용자 없음 - {}", userUpdateDTO.getUserEmail());
                    return new RuntimeException("사용자를 찾을 수 없습니다.");
                });

        if (userUpdateDTO.getUserName() != null) {
            user.setUserName(userUpdateDTO.getUserName());
        }
        if (userUpdateDTO.getUserPassword() != null) {
            user.setUserPassword(passwordEncoder.encode(userUpdateDTO.getUserPassword()));
        }

        userRepository.save(user);
        logger.info("회원정보 수정 성공: {}", userUpdateDTO.getUserEmail());
        return "회원정보 수정이 완료되었습니다.";
    }

    // 회원 탈퇴
    public String deleteAccount(String userEmail) {
        logger.info("회원 탈퇴 요청: {}", userEmail);

        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> {
                    logger.warn("회원 탈퇴 실패: 사용자 없음 - {}", userEmail);
                    return new RuntimeException("사용자를 찾을 수 없습니다.");
                });

        userRepository.delete(user);
        logger.info("회원 탈퇴 성공: {}", userEmail);
        return "회원탈퇴가 완료되었습니다.";
    }
}
