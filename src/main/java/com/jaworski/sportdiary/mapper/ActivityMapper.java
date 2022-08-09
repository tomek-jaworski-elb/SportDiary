package com.jaworski.sportdiary.mapper;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.domain.Distance;
import com.jaworski.sportdiary.domain.User;
import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.entity.UserEntity;
import com.jaworski.sportdiary.repository.UserEntityRepository;
import com.jaworski.sportdiary.service.authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityMapper {

    private final AuthenticationService authenticationService;
    private final UserEntityRepository userEntityRepository;

    private UserEntity getCurrentUser() {
        return userEntityRepository.findByFirstName(authenticationService.getCurrentUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public ActivityEntity activityToEntity(Activity activity) {
        ActivityEntity result = new ActivityEntity();
        result.setDateTime(activity.getDateTime());
        result.setSport(activity.getSport());
        result.setDuration(activity.getDuration());
        result.setAddedAt(activity.getAddedAt());
        result.setUnit(activity.getDistance().getUnits());
        result.setLastModifiedAt(activity.getLastModifiedAt());
        result.setDistanceOf(activity.getDistance().getDistanceOf());
        result.setDeleted(activity.isDeleted());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            result.setUserEntity(getCurrentUser());
        } else {
            result.setUserEntity(getAdminUser());
        }
        return result;
    }

    public Activity entityToActivity(ActivityEntity activityEntity) {
        Activity result = new Activity();
        result.setId(activityEntity.getId().toString());
        result.setDateTime(activityEntity.getDateTime());
        result.setSport(activityEntity.getSport());
        result.setAddedAt(activityEntity.getAddedAt());
        result.setDuration(activityEntity.getDuration());
        result.setLastModifiedAt(activityEntity.getLastModifiedAt());
        result.setDistance(new Distance(activityEntity.getDistanceOf(), activityEntity.getUnit()));
        result.setDeleted(activityEntity.isDeleted());
        UserEntity userEntity = activityEntity.getUserEntity();
        User user = new User();
        user.setId(userEntity.getId());
        user.setFirstName(userEntity.getFirstName());
        user.setEmail(userEntity.getEmail());
        result.setUser(user);
        return result;
    }

    public List<ActivityEntity> activityListToEntityList(List<Activity> activityList) {
        List<ActivityEntity> result = new ArrayList<>();
        for (Activity activity : activityList) {
            result.add(activityToEntity(activity));
        }
        return result;
    }

    private UserEntity getAdminUser() {
        return userEntityRepository.findUserEntityByRoles("Admin");
    }
}
