package com.jaworski.sportdiary.service.DBActivity;

import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.entity.controll.DBActivityManager;
import com.jaworski.sportdiary.service.activity.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DBActivityLoader {

    private final ActivityConverter activityConverter;
    private final DBActivityManager dbActivityManager;
    private final ActivityService activityService;

    public void loadDB() {
        List<ActivityEntity> activitiesDB = activityConverter.ActivityListToEntityList(activityService.getActivityList());

        dbActivityManager.saveAll(activitiesDB);
    }
}
