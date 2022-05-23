package com.jaworski.sportdiary.entity.controll;

import com.jaworski.sportdiary.entity.Activity;
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
    public Activity find(Long id) {
        return entityManager.find(Activity.class, id);
    }

    @Override
    public void save(Activity activity) {
        entityManager.persist(activity);
    }

    @Override
    public void delete(Activity activity) {
        entityManager.remove(activity);
    }

    @Override
    public Activity update(Activity activity) {
        entityManager.merge(activity);
        return activity;
    }

    @Override
    public List<Activity> findAll() {
        return entityManager.createQuery("SELECT a FROM Activity a", Activity.class).getResultList();
    }
}

