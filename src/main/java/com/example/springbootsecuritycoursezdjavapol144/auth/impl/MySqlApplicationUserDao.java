package com.example.springbootsecuritycoursezdjavapol144.auth.impl;

import com.example.springbootsecuritycoursezdjavapol144.auth.ApplicationUser;
import com.example.springbootsecuritycoursezdjavapol144.auth.ApplicationUserDao;
import com.example.springbootsecuritycoursezdjavapol144.repository.ApplicationUserRepository;
import com.example.springbootsecuritycoursezdjavapol144.security.ApplicationUserRole;
import com.example.springbootsecuritycoursezdjavapol144.user.entity.ApplicationUserEntity;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.springbootsecuritycoursezdjavapol144.security.ApplicationUserRole.*;

@Repository("mysql")
public class MySqlApplicationUserDao implements ApplicationUserDao {
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserRepository applicationUserRepository;

    public MySqlApplicationUserDao(PasswordEncoder passwordEncoder, ApplicationUserRepository applicationUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers().stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        return applicationUserRepository
                .findAll() // List<ApplicationUserEntity>
                .stream()
                .map(this::mapEntityToModel).collect(Collectors.toList());
    }

    private ApplicationUser mapEntityToModel(ApplicationUserEntity entity) {
        return new ApplicationUser(
                entity.getUsername(),
                entity.getPassword(),
                entity.getAuthorities()
                        .stream()
                        .flatMap(authority -> ApplicationUserRole.valueOf(
                                authority.getAuthority()).getGrantedAuthorities().stream())
                        .collect(Collectors.toSet()),
                entity.isAccountNonExpired(),
                entity.isAccountNonLocked(),
                entity.isCredentialsNonExpired(),
                entity.isEnabled()
        );
    }
}
