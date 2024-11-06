package com.example.back.user.controller;

import com.example.back.user.dto.SignupDTO;
import com.example.back.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupDTO signupDTO) {
        return userService.signup(signupDTO);
    }
}
