package org.crolopez.infrastructure.adapter;

import org.crolopez.domain.port.service.UserService;
import org.crolopez.domain.model.UserEntity;
import org.crolopez.infrastructure.dto.ApiResponse;
import org.crolopez.infrastructure.dto.UserDTO;
import org.crolopez.infrastructure.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
            .map(userMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(userDTOs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable Long id) {
        UserEntity user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(ApiResponse.success(userMapper.toDTO(user)));
        } else {
            return ResponseEntity.ok(ApiResponse.error("User not found"));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody UserDTO userDTO) {
        UserEntity user = userMapper.toDomain(userDTO);
        UserEntity createdUser = userService.createUser(user);
        return ResponseEntity.ok(ApiResponse.success(userMapper.toDTO(createdUser)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserEntity user = userMapper.toDomain(userDTO);
        UserEntity updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok(ApiResponse.success(userMapper.toDTO(updatedUser)));
        } else {
            return ResponseEntity.ok(ApiResponse.error("User not found"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok(ApiResponse.success(null));
        } else {
            return ResponseEntity.ok(ApiResponse.error("User not found"));
        }
    }
} 