package com.example.springbootsendactivationlink.repository;

import com.example.springbootsendactivationlink.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<UserEntity, String> {
    UserEntity findByEmailIdIgnoreCase(String emailId);
}
