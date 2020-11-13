package com.example.springbootsendactivationlink.service.serviceImpl;

import com.example.springbootsendactivationlink.entity.ConfirmationToken;
import com.example.springbootsendactivationlink.repository.ConformationTokenRepository;
import com.example.springbootsendactivationlink.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    @Autowired
    private ConformationTokenRepository conformationTokenRepository;

    @Override
    public ConfirmationToken findByConfirmationToken(String confirmationToken) {
        return conformationTokenRepository.findByConfirmationToken(confirmationToken) ;
    }

    @Override
    public ConfirmationToken save(ConfirmationToken confirmationToken) {
        return conformationTokenRepository.save(confirmationToken);
    }
}
