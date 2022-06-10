package com.jaworski.sportdiary.entity.repository;

import com.jaworski.sportdiary.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActivityEntityRepository extends JpaRepository<ActivityEntity, Long> {

    Optional<ActivityEntity> findById(UUID activityId);

}
