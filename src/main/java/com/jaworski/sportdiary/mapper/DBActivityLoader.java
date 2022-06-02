package com.jaworski.sportdiary.mapper;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.entity.controll.tmp.DBActivityManager;
import com.jaworski.sportdiary.service.activity.ActivityService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DBActivityLoader {

    private  ActivityMapper activityMapper;
    private  DBActivityManager dbActivityManager;
    private  ActivityService activityService;

    public void loadDB() {
        List<Activity> activityList = activityService.getActivityList();
        List<ActivityEntity> activitiesDB = activityMapper.ActivityListToEntityList(activityList);
        dbActivityManager.saveAll(activitiesDB);
    }
}
