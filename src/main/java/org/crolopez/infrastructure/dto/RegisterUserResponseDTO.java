package org.crolopez.infrastructure.dto;

import lombok.Data;

@Data
public class RegisterUserResponseDTO {
    private String username;
    private String email;
}