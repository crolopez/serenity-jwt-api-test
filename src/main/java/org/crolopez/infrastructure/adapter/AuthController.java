package org.crolopez.infrastructure.adapter;

import org.crolopez.domain.port.service.AuthService;
import org.crolopez.domain.model.AuthRequest;
import org.crolopez.domain.model.UserEntity;
import org.crolopez.infrastructure.dto.ApiResponse;
import org.crolopez.infrastructure.mapper.AuthMapper;
import org.crolopez.infrastructure.dto.AuthRequestDTO;
import org.crolopez.infrastructure.dto.UserDTO;
import org.crolopez.infrastructure.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private AuthMapper authMapper;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody AuthRequestDTO authRequestDTO) {
        AuthRequest authRequest = authMapper.toDomain(authRequestDTO);
        String token = authService.login(authRequest);
        if (token != null) {
            return ResponseEntity.ok(ApiResponse.success(token));
        } else {
            return ResponseEntity.ok(ApiResponse.error("Invalid credentials"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> register(@RequestBody UserDTO userDTO) {
        UserEntity user = userMapper.toDomain(userDTO);
        UserEntity registeredUser = authService.register(user);
        if (registeredUser != null) {
            return ResponseEntity.ok(ApiResponse.success(userMapper.toDTO(registeredUser)));
        } else {
            return ResponseEntity.ok(ApiResponse.error("Error registering user"));
        }
    }
} 