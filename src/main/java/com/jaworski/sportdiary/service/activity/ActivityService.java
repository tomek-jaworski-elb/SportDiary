package com.jaworski.sportdiary.service.activity;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.entity.repository.ActivityEntityRepository;
import com.jaworski.sportdiary.mapper.ActivityMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private static final Logger LOGGER = LogManager.getLogger(ActivityService.class);
    private final List<Activity> activityList;
    private final ActivityMapper activityMapper;
    private final ActivityEntityRepository activityEntityRepository;


    public List<Activity> getActivityList() {
        List<Activity> result = new ArrayList<>();
        Iterable<ActivityEntity> all = activityEntityRepository.findAll();
        all.forEach(activityEntity -> result.add(activityMapper.EntityToActivity(activityEntity)));
        return result;
    }

    public void delete(UUID id) {
        activityEntityRepository.findById(id).ifPresent(activityEntity -> {
            activityEntityRepository.delete(activityEntity);
            LOGGER.info("Deleted activity with id: " + id);
        });
    }

    public void setActivityList() {
        activityList.clear();
        activityEntityRepository.findAll().forEach(activityEntity -> activityList.add(activityMapper.EntityToActivity(activityEntity)));
    }

    public void addActivity(Activity activity) {
        activity.setAddedAt(LocalDateTime.now());
        activityEntityRepository.save(activityMapper.ActivityToEntity(activity));
    }


    public List<Activity> sort(Comparator<Activity> comparator) {
        return activityList.stream()
                .sorted(comparator)
                .toList();
    }

    public Activity getActivity(UUID id) {
        Optional<ActivityEntity> byId = activityEntityRepository.findById(id);
        return byId.map(activityMapper::EntityToActivity).orElse(new Activity());
    }

    public Activity update(Activity activity) {
        return activityEntityRepository.findById(UUID.fromString(activity.getId()))
                .map(activityEntity -> {
                    activityEntity.setDistanceOf(activity.getDistance().getDistanceOf());
                    activityEntity.setUnit(activity.getDistance().getUnits());
                    activityEntity.setDateTime(activity.getDateTime());
                    activityEntity.setDuration(activity.getDuration());
                    activityEntity.setSport(activity.getSport());
                    activityEntity.setAddedAt(activity.getAddedAt());
                    activityEntity.setLastModifiedAt(LocalDateTime.now());
                    activityEntityRepository.save(activityEntity);
                    return activityMapper.EntityToActivity(activityEntity);
                })
                .orElse(new Activity());
    }

    public List<Activity> getActivitiesByDate(LocalDateTime date) {
        return activityEntityRepository.findAll(Sort.by(Sort.Direction.ASC, "dateTime")).stream()
                .filter(activityEntity -> activityEntity.getDateTime().toLocalDate().isEqual(date.toLocalDate()))
                .map(activityMapper::EntityToActivity)
                .collect(Collectors.toList());
    }
}
