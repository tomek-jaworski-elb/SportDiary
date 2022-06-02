package com.jaworski.sportdiary.entity.repository;

import com.jaworski.sportdiary.entity.ActivityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface ActivityEntityRepository extends CrudRepository<ActivityEntity, Long> {

}
