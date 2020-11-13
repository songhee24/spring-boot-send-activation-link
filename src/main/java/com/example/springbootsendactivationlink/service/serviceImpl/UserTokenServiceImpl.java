package com.example.springbootsendactivationlink.service.serviceImpl;

import com.example.springbootsendactivationlink.entity.UserEntity;
import com.example.springbootsendactivationlink.repository.UserRepository;
import com.example.springbootsendactivationlink.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserTokenServiceImpl implements UserTokenService {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity findByEmailIdIgnoreCase(String emailId) {
        return userRepository.findByEmailIdIgnoreCase(emailId);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}
