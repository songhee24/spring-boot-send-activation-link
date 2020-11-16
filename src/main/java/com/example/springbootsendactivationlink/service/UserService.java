package com.example.springbootsendactivationlink.service;

import com.example.springbootsendactivationlink.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    UserEntity findByEmailIgnoreCase(String emailId);

    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

    UserEntity save(UserEntity userEntity);



}
