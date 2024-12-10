package com.example.back.user.service;

import com.example.back.user.entity.User;
import com.example.back.user.dto.LoginDTO;
import com.example.back.user.dto.SignupDTO;
import com.example.back.user.dto.UserProfileDTO;
import com.example.back.user.dto.UserUpdateDTO;
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

    // 회원가입
    public String signup(SignupDTO signupDTO) {
        if (userRepository.existsByUserEmail(signupDTO.getUserEmail())) {
            return "이미 존재하는 이메일입니다.";
        }

        String encodedPassword = passwordEncoder.encode(signupDTO.getUserPassword());
        User newUser = new User();
        newUser.setUserName(signupDTO.getUserName());
        newUser.setUserPassword(encodedPassword);
        newUser.setUserEmail(signupDTO.getUserEmail());

        userRepository.save(newUser);
        return "회원가입이 성공적으로 완료되었습니다.";
    }

    // 로그인
    public String login(LoginDTO loginDTO) {
        Optional<User> optionalUser = userRepository.findByUserEmail(loginDTO.getUserEmail());
        if (optionalUser.isEmpty()) {
            return "이메일이 존재하지 않습니다.";
        }

        User user = optionalUser.get();
        if (passwordEncoder.matches(loginDTO.getUserPassword(), user.getUserPassword())) {
            return "로그인 성공";
        } else {
            return "이메일 또는 비밀번호가 잘못되었습니다.";
        }
    }

    // 회원정보 조회
    public UserProfileDTO getProfile(String userEmail) {
        Optional<User> optionalUser = userRepository.findByUserEmail(userEmail);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }

        User user = optionalUser.get();
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUserEmail(user.getUserEmail());
        userProfileDTO.setUserName(user.getUserName());
        userProfileDTO.setUserPassword(user.getUserPassword());

        return userProfileDTO;
    }

    // 회원정보 수정
    public String updateProfile(UserUpdateDTO userUpdateDTO) {
        Optional<User> optionalUser = userRepository.findByUserEmail(userUpdateDTO.getUserEmail());
        if (optionalUser.isEmpty()) {
            return "사용자를 찾을 수 없습니다.";
        }

        User user = optionalUser.get();

        if (userUpdateDTO.getUserName() != null) {
            user.setUserName(userUpdateDTO.getUserName());
        }
        if (userUpdateDTO.getUserPassword() != null) {
            user.setUserPassword(passwordEncoder.encode(userUpdateDTO.getUserPassword()));
        }
        if (userUpdateDTO.getUserEmail() != null) {
            user.setUserEmail(userUpdateDTO.getUserEmail());
        }

        userRepository.save(user);
        return "회원정보 수정이 완료되었습니다.";
    }

    // 회원 탈퇴
    public String deleteAccount(String userEmail) {
        Optional<User> optionalUser = userRepository.findByUserEmail(userEmail);
        if (optionalUser.isEmpty()) {
            return "사용자를 찾을 수 없습니다.";
        }

        userRepository.delete(optionalUser.get());
        return "회원탈퇴가 완료되었습니다.";
    }
}
