package org.crolopez.domain.model;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
} 