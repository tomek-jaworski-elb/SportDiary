package com.jaworski.sportdiary.entity.controll.tmp;

import com.jaworski.sportdiary.entity.ActivityEntity;

import java.util.List;

public interface ActivityManager {

    ActivityEntity find(Long id);

    void save(ActivityEntity activity);

    void delete(ActivityEntity activity);

    ActivityEntity update(ActivityEntity activity);

    List<ActivityEntity> findAll();
}
