package com.jaworski.sportdiary.entity.controll;

import com.jaworski.sportdiary.entity.Activity;

import java.util.List;

public interface ActivityManager {

    Activity find(Long id);

    void save(Activity activity);

    void delete(Activity activity);

    Activity update(Activity activity);

    List<Activity> findAll();
}
