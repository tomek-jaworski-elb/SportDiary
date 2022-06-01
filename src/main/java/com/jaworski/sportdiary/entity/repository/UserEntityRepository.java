package com.jaworski.sportdiary.entity.repository;

import com.jaworski.sportdiary.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByFirstName(String username);
}
