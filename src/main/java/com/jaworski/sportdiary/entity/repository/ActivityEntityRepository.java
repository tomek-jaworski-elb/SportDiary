package com.jaworski.sportdiary.entity.repository;

import com.jaworski.sportdiary.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface ActivityEntityRepository extends JpaRepository<ActivityEntity, UUID> {

    Optional<ActivityEntity> findById(UUID activityId);

}
