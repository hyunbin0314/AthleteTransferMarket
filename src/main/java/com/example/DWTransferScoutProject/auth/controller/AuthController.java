package com.example.DWTransferScoutProject.auth.controller;

import com.example.DWTransferScoutProject.auth.security.JwtUtil;
import com.example.DWTransferScoutProject.common.account.dto.BaseAccountDto;
import com.example.DWTransferScoutProject.common.account.dto.LoginDto;
import com.example.DWTransferScoutProject.auth.service.AuthService;
import com.example.DWTransferScoutProject.common.account.service.BaseAccountService;
import com.example.DWTransferScoutProject.superadmin.dto.SuperAdminDto;
import com.example.DWTransferScoutProject.superadmin.service.SuperAdminService;
import com.example.DWTransferScoutProject.user.dto.UserDto;
import com.example.DWTransferScoutProject.user.entity.User;
import com.example.DWTransferScoutProject.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final SuperAdminService adminService;

    @Autowired
    public AuthController(AuthService authService, JwtUtil jwtUtil, UserService userService, SuperAdminService adminService) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.adminService = adminService;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody BaseAccountDto accountDto) {
        try {
            if (accountDto.getAccountId() == null || accountDto.getPassword() == null || accountDto.getEmail() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("필수 필드가 누락되었습니다.");
            }

            if (accountDto instanceof UserDto) {
                authService.signUp((UserDto) accountDto, userService);
            } else if (accountDto instanceof SuperAdminDto) {
                authService.signUp((SuperAdminDto) accountDto, adminService);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("알 수 없는 계정 유형입니다.");
            }

            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("회원가입 중 오류가 발생했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 오류가 발생했습니다.");
        }
    }

    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDao, HttpServletResponse response) {
        try {
            String token = authService.login(loginDao, response); // 로그인 서비스에서 토큰 생성 및 반환
            return ResponseEntity.ok(token); // 클라이언트에게 토큰을 반환
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("로그인 중 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }

    // 로그아웃 API
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        String token = jwtUtil.resolveToken(request);
        log.info("Logout token: {}", token);
        if (token != null && jwtUtil.validateToken(token)) {
            Cookie jwtCookie = new Cookie("Authorization", null);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(0);
            response.addCookie(jwtCookie);
            return ResponseEntity.ok("로그아웃이 성공적으로 완료되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }
    }

    // ID 중복 확인 API
    @PostMapping("/idcheck")
    public ResponseEntity<?> idCheck(@RequestBody UserDto userDto) {
        Optional<User> optionalUser = authService.findByUsername(userDto.getUsername());
        if (optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok().build();
    }

    // 토큰으로 사용자 유형 확인 API
    @GetMapping("/user/type")
    public ResponseEntity<?> getUserType(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid token");
        }

        String userType = jwtUtil.getAccountTypeFromToken(token);
        return ResponseEntity.ok(Collections.singletonMap("userType", userType)); // userType으로 반환
    }
}
