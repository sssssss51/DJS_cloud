package com.example.back.user.service;

import com.example.back.user.dto.SignupDTO;
import com.example.back.user.entity.User;
import com.example.back.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String signup(SignupDTO signupDTO) {
        // 이메일 중복 확인
        if (userRepository.existsByUserEmail(signupDTO.getUserEmail())) {
            return "이미 존재하는 이메일입니다.";
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signupDTO.getUserPassword());

        // 사용자 생성 및 저장
        User newUser = new User();
        newUser.setUserName(signupDTO.getUserName());
        newUser.setUserPassword(encodedPassword);
        newUser.setUserEmail(signupDTO.getUserEmail());

        userRepository.save(newUser);
        return "회원가입이 성공적으로 완료되었습니다.";
    }
}


