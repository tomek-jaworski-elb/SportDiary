package com.jaworski.sportdiary.entity.repository;

import com.jaworski.sportdiary.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserEntityRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByFirstName(String username);
}
