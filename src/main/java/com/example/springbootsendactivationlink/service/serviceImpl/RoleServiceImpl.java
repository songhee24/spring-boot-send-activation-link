package com.example.springbootsendactivationlink.service.serviceImpl;

import com.example.springbootsendactivationlink.entity.RoleEntity;
import com.example.springbootsendactivationlink.model.RoleName;
import com.example.springbootsendactivationlink.repository.RoleRepository;
import com.example.springbootsendactivationlink.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<RoleEntity> findByName(RoleName roleName) {
        return roleRepository.findByName(roleName);
    }
}
