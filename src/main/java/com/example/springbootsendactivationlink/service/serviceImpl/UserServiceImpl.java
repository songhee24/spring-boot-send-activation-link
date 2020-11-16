package com.example.springbootsendactivationlink.service.serviceImpl;

import com.example.springbootsendactivationlink.entity.UserEntity;
import com.example.springbootsendactivationlink.repository.UserRepository;
import com.example.springbootsendactivationlink.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity findByEmailIgnoreCase(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public Optional<UserEntity> findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email);
    }
}
