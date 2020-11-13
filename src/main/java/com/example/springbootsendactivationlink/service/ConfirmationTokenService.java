package com.example.springbootsendactivationlink.service;

import com.example.springbootsendactivationlink.entity.ConfirmationToken;

public interface ConfirmationTokenService {
    ConfirmationToken findByConfirmationToken(String confirmationToken);

    ConfirmationToken save(ConfirmationToken confirmationToken);
}
