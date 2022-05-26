package com.jaworski.sportdiary.mapper;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.entity.controll.DBActivityManager;
import com.jaworski.sportdiary.service.activity.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DBActivityLoader {

    private final ActivityMapper activityMapper;
    private final DBActivityManager dbActivityManager;
    private final ActivityService activityService;

    public void loadDB() {
        List<Activity> activityList = activityService.getActivityList();
        List<ActivityEntity> activitiesDB = activityMapper.ActivityListToEntityList(activityList);

        dbActivityManager.saveAll(activitiesDB);
    }
}
