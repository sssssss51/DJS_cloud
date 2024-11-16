package com.example.back.user.service;

import com.example.back.user.dto.LoginDTO;
import com.example.back.user.dto.SignupDTO;
import com.example.back.user.entity.User;
import com.example.back.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    public String login(LoginDTO loginDTO) {
        // 이메일로 사용자 검색
        Optional<User> optionalUser = userRepository.findByUserEmail(loginDTO.getUserEmail());
        if (optionalUser.isEmpty()) {
            return "이메일이 존재하지 않습니다.";
        }

        User user = optionalUser.get();

        // 비밀번호 확인
        if (passwordEncoder.matches(loginDTO.getUserPassword(), user.getUserPassword())) {
            return "로그인 성공";
        } else {
            return "비밀번호가 일치하지 않습니다.";
        }
    }
}
