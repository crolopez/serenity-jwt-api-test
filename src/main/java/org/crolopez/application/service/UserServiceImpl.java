package org.crolopez.application.service;

import org.crolopez.domain.exception.*;
import org.crolopez.domain.model.UserEntity;
import org.crolopez.domain.port.repository.UserRepository;
import org.crolopez.domain.port.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyRegisteredException(user.getEmail());
        }
        return userRepository.save(user);
    }

    @Override
    public UserEntity updateUser(Long id, UserEntity user) {
        return userRepository.findById(id)
            .map(existingUser -> {
                if (!existingUser.getUsername().equals(user.getUsername()) && 
                    userRepository.findByUsername(user.getUsername()) != null) {
                    throw new UsernameAlreadyExistsException(user.getUsername());
                }
                if (!existingUser.getEmail().equals(user.getEmail()) && 
                    userRepository.findByEmail(user.getEmail()) != null) {
                    throw new EmailAlreadyRegisteredException(user.getEmail());
                }
                existingUser.setUsername(user.getUsername());
                existingUser.setEmail(user.getEmail());
                existingUser.setRole(user.getRole());
                return userRepository.save(existingUser);
            })
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return false;
    }
} 