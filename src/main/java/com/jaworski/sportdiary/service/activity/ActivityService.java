package com.jaworski.sportdiary.service.activity;

import com.jaworski.sportdiary.config.security.UserPrincipal;
import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.entity.UserEntity;
import com.jaworski.sportdiary.mapper.ActivityMapper;
import com.jaworski.sportdiary.repository.ActivityEntityRepository;
import com.jaworski.sportdiary.repository.UserEntityRepository;
import com.jaworski.sportdiary.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {

    private final List<Activity> activityList;
    private final ActivityMapper activityMapper;
    private final UserEntityRepository userEntityRepository;
    private final AuthenticationService authenticationService;
    private final ActivityEntityRepository activityEntityRepository;

    private UserEntity getCurrentUser() {
        return userEntityRepository.findByFirstName(authenticationService.getCurrentUserName());
    }

    public List<Activity> getActivityList() {
        List<Activity> result = new ArrayList<>();
        Iterable<ActivityEntity> all = activityEntityRepository.findAll();
        all.forEach(activityEntity -> result.add(activityMapper.EntityToActivity(activityEntity)));
        return result;
    }

    public void delete(UUID id) {
        activityEntityRepository.findById(id).ifPresent(activityEntity -> {
            activityEntity.setDeleted(true);
            activityEntity.setLastModifiedAt(LocalDateTime.now());
            activityEntityRepository.save(activityEntity);
            log.info("Deleted activity with id: {}", id);
        });
    }

    public void setActivityList() {
        activityList.clear();
        activityEntityRepository.findAll().forEach(activityEntity -> activityList.add(activityMapper.EntityToActivity(activityEntity)));
    }

    public Activity addActivity(Activity activity) {
        activity.setAddedAt(LocalDateTime.now());
        activity.setLastModifiedAt(LocalDateTime.now());
        ActivityEntity entity = new ActivityEntity(activity.getDateTime(), activity.getAddedAt(), activity.getLastModifiedAt(),
                activity.getDuration(), activity.getDistance().getDistanceOf(), activity.getDistance().getUnits(),
                activity.getSport(), getCurrentUser());
        ActivityEntity save = activityEntityRepository.save(entity);
        return activityMapper.EntityToActivity(save);
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
                .toList();
    }

    public List<Activity> getUserActivityList(UserPrincipal userPrincipal, boolean showIsDeleted) {
        return activityEntityRepository.findAll(Sort.by(Sort.Direction.ASC, "dateTime")).stream()
                .filter(activityEntity -> {
                    if (userPrincipal == null) {
                        return true;
                    } else {
                        return activityEntity.getUserEntity().getId().equals(userPrincipal.getId());
                    }
                })
                .filter(activityEntity -> {
                    if (showIsDeleted) {
                        return true;
                    } else {
                        return !activityEntity.isDeleted();
                    }
                })
                .map(activityMapper::EntityToActivity)
                .toList();
    }

    public void restore(UUID id) {
        activityEntityRepository.findById(id).ifPresent(activityEntity -> {
            activityEntity.setDeleted(false);
            activityEntity.setLastModifiedAt(LocalDateTime.now());
            activityEntityRepository.save(activityEntity);
            log.info("Deleted activity with id: {}", id);
        });
    }

    public Page<Activity> getPage(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ActivityEntity> all = activityEntityRepository.findAll(Sort.by("duration").descending());
        List<Activity> activities = all.stream().map(activityMapper::EntityToActivity).toList();
        List<Activity> list;
        if (activities.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, activities.size());
            list = activities.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), activities.size());
    }

    public List<Activity> getUserActivities(UUID id) {
        List<ActivityEntity> activitiesByUserId = activityEntityRepository.findActivitiesByUserId(id);
        return activitiesByUserId.stream()
                .map(activityMapper::EntityToActivity)
                .toList();
    }

    public List<Activity> getAllActivities() {
        activityEntityRepository.findAll();
        return activityEntityRepository.findAll().stream()
                .map(activityMapper::EntityToActivity)
                .toList();
    }
}
