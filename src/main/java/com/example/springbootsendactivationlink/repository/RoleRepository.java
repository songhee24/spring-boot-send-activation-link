package com.example.springbootsendactivationlink.repository;

import com.example.springbootsendactivationlink.entity.RoleEntity;
import com.example.springbootsendactivationlink.model.RoleName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity,Long> {
    Optional<RoleEntity> findByName(RoleName roleName);
}
