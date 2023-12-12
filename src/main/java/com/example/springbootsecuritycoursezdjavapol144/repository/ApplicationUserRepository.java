package com.example.springbootsecuritycoursezdjavapol144.repository;

import com.example.springbootsecuritycoursezdjavapol144.user.entity.ApplicationUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // można dodać informacyjnie
public interface ApplicationUserRepository extends JpaRepository<ApplicationUserEntity, Long> {
}
