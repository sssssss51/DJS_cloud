package com.example.back.user.controller;

import com.example.back.user.dto.LoginDTO;
import com.example.back.user.dto.SignupDTO;
import com.example.back.user.dto.UserProfileDTO;
import com.example.back.user.dto.UserUpdateDTO;
import com.example.back.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    /**
     * 회원가입 API 엔드포인트
     * 사용자가 회원가입 요청을 보낼 때 호출됩니다.
     *
     * @param signupDTO 회원가입 요청 데이터를 담은 객체
     * @return 회원가입 성공 또는 실패 메시지
     */
    @Operation(summary = "회원가입", description = "사용자가 회원가입을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/signup")
    public String signup(@RequestBody SignupDTO signupDTO) {
        // 회원가입 요청을 처리하고 결과를 반환합니다.
        return userService.signup(signupDTO);
    }

    /**
     * 로그인 API 엔드포인트
     * 사용자가 로그인 요청을 보낼 때 호출됩니다.
     *
     * @param loginDTO 로그인 요청 데이터를 담은 객체
     * @return 로그인 성공 또는 실패 메시지
     */
    @Operation(summary = "로그인", description = "사용자가 로그인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        // 로그인 요청을 처리하고 결과를 반환합니다.
        return userService.login(loginDTO);
    }

    /**
     * 프로필 조회 API 엔드포인트
     * 이메일을 기준으로 사용자의 프로필 정보를 조회합니다.
     *
     * @param userEmail 조회할 사용자의 이메일
     * @return 사용자 프로필 정보
     */
    @Operation(summary = "프로필 조회", description = "이메일을 기준으로 사용자의 프로필 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/profile/{userEmail}")
    public UserProfileDTO getProfile(@PathVariable String userEmail) {
        // 이메일로 사용자 프로필 정보를 조회하고 반환합니다.
        return userService.getProfile(userEmail);
    }

    /**
     * 프로필 수정 API 엔드포인트
     * 사용자의 프로필 정보를 수정합니다.
     *
     * @param userUpdateDTO 수정할 데이터를 담은 객체
     * @return 수정 성공 또는 실패 메시지
     */
    @Operation(summary = "프로필 수정", description = "사용자의 프로필 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 수정 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PutMapping("/update")
    public String updateProfile(@RequestBody UserUpdateDTO userUpdateDTO) {
        // 프로필 수정 요청을 처리하고 결과를 반환합니다.
        return userService.updateProfile(userUpdateDTO);
    }

    /**
     * 회원 탈퇴 API 엔드포인트
     * 이메일을 기준으로 사용자를 삭제합니다.
     *
     * @param userEmail 삭제할 사용자의 이메일
     * @return 삭제 성공 또는 실패 메시지
     */
    @Operation(summary = "회원 탈퇴", description = "이메일을 기준으로 사용자를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @DeleteMapping("/delete/{userEmail}")
    public String deleteAccount(@PathVariable String userEmail) {
        // 회원 탈퇴 요청을 처리하고 결과를 반환합니다.
        return userService.deleteAccount(userEmail);
    }
}
