package org.crolopez.infrastructure.dto;

import lombok.Data;

@Data
public class RegisterUserRequestDTO {
    private String username;
    private String password;
    private String email;
    private String role;
}