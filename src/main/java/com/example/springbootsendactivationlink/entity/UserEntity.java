package com.example.springbootsendactivationlink.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min=5, max=15)
    private String password;

    @NotNull
    @Size(min=5, max=15)
    @Column(name = "username")
    private String username;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roleEntities;

    private boolean isEnabled;

    public UserEntity() {
    }

    public Set<RoleEntity> getRoleEntities() {
        return roleEntities;
    }

    public void setRoleEntities(Set<RoleEntity> roleEntities) {
        this.roleEntities = roleEntities;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
