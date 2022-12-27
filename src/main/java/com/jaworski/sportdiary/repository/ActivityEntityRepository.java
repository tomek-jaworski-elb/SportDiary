package com.jaworski.sportdiary.repository;

import com.jaworski.sportdiary.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActivityEntityRepository extends JpaRepository<ActivityEntity, UUID> {

    Optional<ActivityEntity> findById(UUID activityId);

    @Query(value = "SELECT a FROM ActivityEntity a WHERE a.userEntity.id = :id")
    List<ActivityEntity> findActivitiesByUserId(UUID id);
}
