package com.example.springbootsendactivationlink.repository;

import com.example.springbootsendactivationlink.entity.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConformationTokenRepository extends CrudRepository<ConfirmationToken,String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}
