package com.example.springbootsendactivationlink.repository;

import com.example.springbootsendactivationlink.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
    UserEntity findByEmailIgnoreCase(String email);

    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

}
