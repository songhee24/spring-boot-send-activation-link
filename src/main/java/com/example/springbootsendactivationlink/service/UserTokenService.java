package com.example.springbootsendactivationlink.service;

import com.example.springbootsendactivationlink.entity.UserEntity;

public interface UserTokenService {
    UserEntity findByEmailIdIgnoreCase(String emailId);

    UserEntity save(UserEntity userEntity);
}
