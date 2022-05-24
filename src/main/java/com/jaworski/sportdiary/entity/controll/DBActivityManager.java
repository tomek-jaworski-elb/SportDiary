package com.jaworski.sportdiary.entity.controll;

import com.jaworski.sportdiary.entity.ActivityEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class DBActivityManager implements ActivityManager {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ActivityEntity find(Long id) {
        return entityManager.find(ActivityEntity.class, id);
    }

    @Override
    public void save(ActivityEntity activity) {
        entityManager.persist(activity);
    }

    @Override
    public void delete(ActivityEntity activity) {
        entityManager.remove(activity);
    }

    @Override
    public ActivityEntity update(ActivityEntity activity) {
        entityManager.merge(activity);
        return activity;
    }

    @Override
    public List<ActivityEntity> findAll() {
        return entityManager.createQuery("SELECT a FROM ActivityEntity a", ActivityEntity.class).getResultList();
    }

    public void saveAll(List<ActivityEntity> activitiesDB) {
        for (ActivityEntity activity : activitiesDB) {
            entityManager.persist(activity);
        }
    }
}

