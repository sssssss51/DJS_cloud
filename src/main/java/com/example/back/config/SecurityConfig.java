package com.example.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
// Spring Security 설정을 담당하는 클래스입니다.
public class SecurityConfig {

    /**
     * PasswordEncoder를 Spring 컨테이너에 빈(Bean)으로 등록합니다.
     * 여기서는 BCryptPasswordEncoder를 사용하여 비밀번호를 안전하게 암호화합니다.
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder는 강력한 해시 함수를 사용하여 비밀번호를 암호화합니다.
        // 암호화 강도(strength)는 12로 설정되어 있으며, 기본값은 10입니다.
        return new BCryptPasswordEncoder(12);
    }
}
