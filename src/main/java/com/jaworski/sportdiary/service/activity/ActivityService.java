package com.jaworski.sportdiary.service.activity;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.repository.ActivityRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Service
public class ActivityService {

    private static final Logger logger = LogManager.getLogger(ActivityService.class);

    private final List<Activity> activities;

    @Autowired
    public ActivityService(ActivityRepository activityRepository, List<Activity> activities) {
        this.activities = activityRepository.getActivities();
    }

    public List<Activity> sort(Comparator<Activity> comparator) {
        return activities.stream()
                .sorted(comparator)
                .toList();
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public Activity update(int id, Activity activity) {
        Activity act = activities.stream()
                .filter(activity1 -> activity1.getId() == id)
                .findFirst()
                .orElse(new Activity());
        int i = activities.indexOf(act);
        activities.set(i, activity);
        return activity;
    }

    public int maxId() {
        return activities.stream()
                .min((a1, a2) -> {
                    return a2.getId() - a1.getId();
                })
                .orElse(new Activity())
                .getId();
    }

    public Activity getActivity(int id) {
        return activities.stream()
                .filter(activity -> activity.getId() == id)
                .findFirst()
                .orElse(new Activity());
    }

    public void delete(int id) {
        Iterator<Activity> iterator = activities.iterator();
        Activity activity;
        while (iterator.hasNext()) {
            activity = iterator.next();
            if (activity.getId() == id) {
                iterator.remove();
            }
        }
    }
}
