package org.crolopez.domain.port.service;

import org.crolopez.domain.model.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> getAllUsers();
    UserEntity getUserById(Long id);
    UserEntity createUser(UserEntity user);
    UserEntity updateUser(Long id, UserEntity user);
    boolean deleteUser(Long id);
} 