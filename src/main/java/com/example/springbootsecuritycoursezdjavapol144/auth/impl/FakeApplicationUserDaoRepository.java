package com.example.springbootsecuritycoursezdjavapol144.auth.impl;

import com.example.springbootsecuritycoursezdjavapol144.auth.ApplicationUser;
import com.example.springbootsecuritycoursezdjavapol144.auth.ApplicationUserDao;
import com.google.common.collect.Lists;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.springbootsecuritycoursezdjavapol144.security.ApplicationUserRole.*;

@Repository("fake")
public class FakeApplicationUserDaoRepository implements ApplicationUserDao {
    private final PasswordEncoder passwordEncoder;

    public FakeApplicationUserDaoRepository(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        return Lists.newArrayList(
                new ApplicationUser(
                        "jarek",
                        passwordEncoder.encode("123456"),
                        ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true),
                new ApplicationUser(
                        "daniel",
                        passwordEncoder.encode("123456"),
                        ADMINTRAINEE.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true),
                new ApplicationUser(
                        "marek",
                        passwordEncoder.encode("123456"),
                        STUDENT.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true),
                new ApplicationUser(
                        "kasia",
                        passwordEncoder.encode("123456"),
                        GUEST.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true)
        );
    }
}
