package com.jaworski.sportdiary.mapper;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.domain.Distance;
import com.jaworski.sportdiary.domain.User;
import com.jaworski.sportdiary.domain.enums.Sport;
import com.jaworski.sportdiary.domain.enums.Unit;
import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class ActivityMapperTest {

    @InjectMocks
    private ActivityMapper activityMapper;

    @Test
    void activityToEntity_returnsActivityEntity_givenActualActivity() {
        Activity activity = new Activity();
        activity.setSport(Sport.GYM);
        activity.setDuration(10L);
        activity.setDateTime(LocalDateTime.of(2020, 1, 1, 1, 1));
        activity.setDistance(new Distance(10.0, Unit.KM));
        activity.setUser(new User());
        ActivityEntity activityEntity = activityMapper.activityToEntity(activity);
        assertThat(activityEntity).isNotNull();
        assertThat(activityEntity.getId()).isNull();
        assertThat(activityEntity.getSport()).isEqualTo(Sport.GYM);
        assertThat(activityEntity.getDuration()).isEqualTo(10L);
        assertThat(activityEntity.getDateTime()).isEqualTo(LocalDateTime.of(2020, 1, 1, 1, 1));
        assertThat(activityEntity.getDistanceOf()).isEqualTo(10.0);
        assertThat(activityEntity.getUnit()).isEqualTo(Unit.KM);
        assertThat(activityEntity.getUserEntity()).isNotNull();
    }

    @Test
    void entityToActivity_returnsActivity_givenActualActivityEntity() {
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setId(UUID.randomUUID());
        activityEntity.setSport(Sport.GYM);
        activityEntity.setDuration(10L);
        activityEntity.setDateTime(LocalDateTime.of(2020, 1, 1, 1, 1));
        activityEntity.setDistanceOf(10.0);
        activityEntity.setUnit(Unit.KM);
        activityEntity.setUserEntity(new UserEntity());
        Activity activity = activityMapper.entityToActivity(activityEntity);
        assertThat(activity).isNotNull();
        assertThat(activity.getId()).isEqualTo(activityEntity.getId().toString());
        assertThat(activity.getSport()).isEqualTo(Sport.GYM);
        assertThat(activity.getDuration()).isEqualTo(10L);
        assertThat(activity.getDateTime()).isEqualTo(LocalDateTime.of(2020, 1, 1, 1, 1));
        assertThat(activity.getDistance()).isNotNull();
        assertThat(activity.getDistance().getDistanceOf()).isEqualTo(10.0);
        assertThat(activity.getDistance().getUnits()).isEqualTo(Unit.KM);
        assertThat(activity.getUser()).isNotNull();
    }

    @Test
    void entityToActivity_returnsActivity_givenEmptyActivityEntity() {
        ActivityEntity activityEntity = new ActivityEntity();
        Activity activity = activityMapper.entityToActivity(activityEntity);
        assertThat(activity).isNotNull();
        assertThat(activity.getId()).isEmpty();
        assertThat(activity.getSport()).isNull();
        assertThat(activity.getDuration()).isNull();
        assertThat(activity.getDateTime()).isNull();
        assertThat(activity.getDistance().getDistanceOf()).isNull();
        assertThat(activity.getDistance().getUnits()).isNull();
        assertThat(activity.getUser()).isNull();
    }

    @Test
    void entityToActivity_returnsActivity_givenNullActivityEntity() {
        Activity activity = activityMapper.entityToActivity(null);
        assertThat(activity).isNotNull();
        assertThat(activity.getId()).isNull();
        assertThat(activity.getSport()).isNull();
        assertThat(activity.getDuration()).isNull();
        assertThat(activity.getDateTime()).isNull();
        assertThat(activity.getDistance()).isNull();
        assertThat(activity.getUser()).isNull();
    }

    @Test
    void entityToActivity_returnsActivityEntity_givenNullActivity() {
        Activity activity = activityMapper.entityToActivity(null);
        assertThat(activity).isNotNull();
        assertThat(activity.getId()).isNull();
        assertThat(activity.getSport()).isNull();
        assertThat(activity.getDuration()).isNull();
        assertThat(activity.getDateTime()).isNull();
        assertThat(activity.getDistance()).isNull();
        assertThat(activity.getUser()).isNull();
    }

}