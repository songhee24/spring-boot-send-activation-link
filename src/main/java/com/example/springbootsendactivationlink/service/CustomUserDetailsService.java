package com.example.springbootsendactivationlink.service;

import com.example.springbootsendactivationlink.entity.UserEntity;
import com.example.springbootsendactivationlink.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {

        //note: Логин с помощью имени пользователя или емаил
        UserEntity userEntity =userService.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail));
        System.err.println("load user by username \n "+ userEntity);
        //создаем UserDetails обьект
        return UserPrincipal.createDetails(userEntity);
    }
}
