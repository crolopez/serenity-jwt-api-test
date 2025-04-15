package org.crolopez.domain.port.service;

import org.crolopez.domain.model.AuthRequest;
import org.crolopez.domain.model.UserEntity;

public interface AuthService {
    String login(AuthRequest authRequest);
    void logout(String token);
    UserEntity register(UserEntity user);
} 