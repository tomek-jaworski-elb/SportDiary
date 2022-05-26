package com.jaworski.sportdiary.mapper;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.domain.Distance;
import com.jaworski.sportdiary.entity.ActivityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityMapper {

    public ActivityEntity ActivityToEntity(Activity activity) {
        ActivityEntity dbActivity = new ActivityEntity();
        // TODO: map Units
        //        dbActivity.setUnit(activity.getDistance().getUnits());
        dbActivity.setDistanceOf(activity.getDistance().getDistanceOf());
        dbActivity.setDuration(activity.getDuration());
        //TODO: map sport
        //        dbActivity.setSport(activity.getSport());
        dbActivity.setDateTime(activity.getDateTime());
        return dbActivity;
    }

    public Activity EntityToActivity(ActivityEntity dbActivity) {
        Activity activity = new Activity();
        Distance distance = new Distance();
        distance.setDistanceOf(dbActivity.getDistanceOf());
        // TODO: map Units
        //        distance.setUnits(dbActivity.getUnit());
        activity.setDistance(distance);
        activity.setDuration(dbActivity.getDuration());
        //TODO: map sport
        //        activity.setSport(dbActivity.getSport());
        activity.setDateTime(dbActivity.getDateTime());
        activity.setId(dbActivity.getId());
        return activity;
    }

    public List<ActivityEntity> ActivityListToEntityList(List<Activity> activityList) {
        List<ActivityEntity> dbActivityList = new ArrayList<>();
        for (Activity activity : activityList) {
            dbActivityList.add(ActivityToEntity(activity));
        }
        return dbActivityList;
    }

    public List<Activity> EntityListToActivityList(List<ActivityEntity> dbActivityList) {
        List<Activity> activityList = new ArrayList<>();
        for (ActivityEntity dbActivity : dbActivityList) {
            activityList.add(EntityToActivity(dbActivity));
        }
        return activityList;
    }
}
