package com.example.springbootsecuritycoursezdjavapol144.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.springbootsecuritycoursezdjavapol144.user.entity.*;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUserEntity, Long> {
}
