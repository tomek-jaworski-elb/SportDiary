package com.jaworski.sportdiary.entity.controll.tmp;

import com.jaworski.sportdiary.entity.SportEntity;
import com.jaworski.sportdiary.entity.controll.DBManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class DBSportManager implements DBManager<SportEntity> {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public <T> T find(Class<T> clazz, Long id) {
        return Optional.ofNullable(entityManager.find(clazz, id)).orElse(null);
    }

    @Override
    public <T> T find(Long id) {
        return null;
    }

    @Override
    public <T> void save(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public <T> void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public <T> T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public <T> List<T> findAll(Class<T> clazz) {
        return entityManager.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e", clazz).getResultList();
    }

    @Override
    public <T> void saveAll(List<T> entities) {
        for (T entity : entities) {
            save(entity);
        }
    }
}
