package com.jaworski.sportdiary.service.DBActivity;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.domain.Distance;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityConverter {

    public com.jaworski.sportdiary.entity.Activity ActivityToEntity(Activity activity) {
        com.jaworski.sportdiary.entity.Activity dbActivity = new com.jaworski.sportdiary.entity.Activity();
        dbActivity.setUnit(activity.getDistance().getUnits());
        dbActivity.setDistanceOf(activity.getDistance().getDistanceOf());
        dbActivity.setDuration(activity.getDuration());
        dbActivity.setSport(activity.getSport());
        dbActivity.setDateTime(activity.getDateTime());
        return dbActivity;
    }

    public Activity EntityToActivity(com.jaworski.sportdiary.entity.Activity dbActivity) {
        Activity activity = new Activity();
        Distance distance = new Distance();
        distance.setDistanceOf(dbActivity.getDistanceOf());
        distance.setUnits(dbActivity.getUnit());
        activity.setDistance(distance);
        activity.setDuration(dbActivity.getDuration());
        activity.setSport(dbActivity.getSport());
        activity.setDateTime(dbActivity.getDateTime());
        activity.setId(dbActivity.getId());
        return activity;
    }

    public List<com.jaworski.sportdiary.entity.Activity> ActivityListToEntityList(List<Activity> activityList) {
        List<com.jaworski.sportdiary.entity.Activity> dbActivityList = new ArrayList<>();
        for (Activity activity : activityList) {
            dbActivityList.add(ActivityToEntity(activity));
        }
        return dbActivityList;
    }
}
