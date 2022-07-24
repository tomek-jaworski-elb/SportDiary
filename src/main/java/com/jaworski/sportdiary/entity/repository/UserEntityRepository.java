package com.jaworski.sportdiary.entity.repository;

import com.jaworski.sportdiary.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByFirstName(String username);

    UserEntity findUserEntityByRoles(String role);
}
