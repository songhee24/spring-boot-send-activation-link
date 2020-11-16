package com.example.springbootsendactivationlink.security;

import com.example.springbootsendactivationlink.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


//note: кастомный класс будет содержать в себе данные которые возваращает нам UserDetailsService
// Spring Security будет использовать информацию, хранящуюся в объекте UserPrincipal,
// для выполнения аутентификации и авторизации.
public class UserPrincipal implements UserDetails {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Boolean isEnable;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String username, String email, String password, Boolean isEnable, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isEnable = isEnable;
        this.authorities = authorities;
    }

    public static UserPrincipal createDetails(UserEntity userEntity){
        List<GrantedAuthority> authorities =userEntity
                .getRoleEntities()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

        System.err.println("UserPrincipal password:" + userEntity.getPassword());
        System.err.println("authorities:" + authorities);

        return new UserPrincipal(
                userEntity.getUserId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                authorities
        );

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
