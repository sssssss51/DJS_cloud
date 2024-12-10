package com.example.back.user.controller;

import com.example.back.user.dto.LoginDTO;
import com.example.back.user.dto.SignupDTO;
import com.example.back.user.dto.UserProfileDTO;
import com.example.back.user.dto.UserUpdateDTO;
import com.example.back.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
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

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    @GetMapping("/profile/{userEmail}")
    public UserProfileDTO getProfile(@PathVariable String userEmail) {
        return userService.getProfile(userEmail);
    }

    @PutMapping("/update")
    public String updateProfile(@RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.updateProfile(userUpdateDTO);
    }

    @DeleteMapping("/delete/{userEmail}")
    public String deleteAccount(@PathVariable String userEmail) {
        return userService.deleteAccount(userEmail);
    }
}
