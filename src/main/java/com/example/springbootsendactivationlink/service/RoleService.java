package com.example.springbootsendactivationlink.service;

import com.example.springbootsendactivationlink.entity.RoleEntity;
import com.example.springbootsendactivationlink.model.RoleName;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<RoleEntity> findByName(RoleName roleName);
}
