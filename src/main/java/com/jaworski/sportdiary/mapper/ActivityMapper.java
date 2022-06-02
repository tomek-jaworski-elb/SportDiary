package com.jaworski.sportdiary.mapper;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.domain.Distance;
import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.entity.UserEntity;
import com.jaworski.sportdiary.entity.repository.UserEntityRepository;
import com.jaworski.sportdiary.service.AuthenticationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityMapper {

    private final AuthenticationService authenticationService;
    private UserEntityRepository userEntityRepository;

    public ActivityMapper(AuthenticationService authenticationService, UserEntityRepository userEntityRepository) {
        this.authenticationService = authenticationService;
        this.userEntityRepository = userEntityRepository;
    }

    private UserEntity getCurrentUser() {
        return userEntityRepository.findByFirstName(authenticationService.getCurrentUserName());
    }

    public ActivityEntity ActivityToEntity(Activity activity) {
        ActivityEntity result = new ActivityEntity();
        result.setDateTime(activity.getDateTime());
        result.setSport(activity.getSport());
        result.setDuration(activity.getDuration());
//        result.setUser(authenticationService.getCurrentUserName());
        result.setUnit(activity.getDistance().getUnits());
        result.setDistanceOf(activity.getDistance().getDistanceOf());
        result.setUserEntity(getCurrentUser());
        return result;
    }

    public Activity EntityToActivity(ActivityEntity activityEntity) {
        Activity result = new Activity();
        result.setId(activityEntity.getId());
        result.setDateTime(activityEntity.getDateTime());
        result.setSport(activityEntity.getSport());
        result.setDuration(activityEntity.getDuration());
        result.setDistance(new Distance(activityEntity.getDistanceOf(), activityEntity.getUnit()));
        UserEntity userEntity = activityEntity.getUserEntity();
        result.setOwner(userEntity.getFirstName());
        return result;
    }

    public List<Activity> EntityListToActivityList(List<ActivityEntity> activityEntityList) {
        List<Activity> result = new ArrayList<>();
        for (ActivityEntity activityEntity : activityEntityList) {
            result.add(EntityToActivity(activityEntity));
        }
        return result;
    }

    public List<ActivityEntity> ActivityListToEntityList(List<Activity> activityList) {
        List<ActivityEntity> result = new ArrayList<>();
        for (Activity activity : activityList) {
            result.add(ActivityToEntity(activity));
        }
        return result;
    }
}
