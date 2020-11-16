package com.example.springbootsendactivationlink.config;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //note: Setting service to find user in database.
        //  And Setting PasswordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.csrf().disable()

                    .authorizeRequests()
                        .antMatchers("/","/home","about").permitAll()
                        .antMatchers("/register","/login","/logout").permitAll()
                        .antMatchers("/confirm").permitAll()

                        //note: '/userInfo' page requires login as ROLE_USER or ROLE_ADMIN.
                        // If no login, it will redirect to /login page.
                        .antMatchers("/userInfo").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")

                        //note For ADMIN only.
                        .antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
                        .anyRequest().authenticated()
                    .and()

                    //note: Config for Login Form
                    .formLogin()
                        //note: Submit URL of login page
                        // Submit URL
                        .loginProcessingUrl("/j_spring_security_check")
                        .loginPage("/login")
                        .defaultSuccessUrl("/userAccountInfo")
                        .failureUrl("/login?error=true")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()


                    //note: Config for Logout Page
                        .and()
                            .logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful")
                            .permitAll()

                        .and()
                            .rememberMe().tokenRepository(this.persistentTokenRepository())
                            .tokenValiditySeconds(1*24*60*60) // 24h

                        //note: When the user has logged in as XX.
                        // But access a page that requires role YY,
                        // AccessDeniedException will be thrown.
                        .and().exceptionHandling().accessDeniedPage("/403");

        ;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }
}
