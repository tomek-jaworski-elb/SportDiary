package com.jaworski.sportdiary.entity.repository;

import com.jaworski.sportdiary.entity.ActivityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActivityEntityRepository extends CrudRepository<ActivityEntity, Long> {

    Optional<ActivityEntity> findById(UUID activityId);

}
