package com.jaworski.sportdiary.service.activity;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.entity.controll.DBEntityManager;
import com.jaworski.sportdiary.mapper.ActivityMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service

public class ActivityService {

    private static final Logger LOGGER = LogManager.getLogger(ActivityService.class);
    private  List<Activity> activityList;
    private DBEntityManager<ActivityEntity> dbActivityEntityManager;
    private ActivityMapper activityMapper;

    public ActivityService(List<Activity> activityList, DBEntityManager<ActivityEntity> dbActivityEntityManager, ActivityMapper activityMapper) {
        this.dbActivityEntityManager = dbActivityEntityManager;
        this.activityList = activityMapper.EntityListToActivityList(dbActivityEntityManager.findAll(ActivityEntity.class));
        this.activityMapper = activityMapper;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void delete(int id) {
        Iterator<Activity> iterator = activityList.iterator();
        Activity activity;
        while (iterator.hasNext()) {
            activity = iterator.next();
            if (activity.getId() == id) {
                iterator.remove();
            }
        }
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public Long maxId() {
        return activityList.stream()
                .min(Comparator.comparing(Activity::getId))
                .orElse(new Activity())
                .getId();
    }

    public List<Activity> sort(Comparator<Activity> comparator) {
        return activityList.stream()
                .sorted(comparator)
                .toList();
    }

    public Activity getActivity(int id) {
        return activityList.stream()
                .filter(activity -> activity.getId() == id)
                .findFirst()
                .orElse(new Activity());
    }

    public Activity update(Long id, Activity activity) {
        Activity act = activityList.stream()
                .filter(a -> Objects.equals(a.getId(), id))
                .findFirst()
                .orElse(new Activity());
        int i = activityList.indexOf(act);
        activityList.set(i, activity);
        return activity;
    }
}
