package com.jaworski.sportdiary.repository;

import com.jaworski.sportdiary.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByFirstName(String username);
    UserEntity findUserEntityByRoles(String role);
}
