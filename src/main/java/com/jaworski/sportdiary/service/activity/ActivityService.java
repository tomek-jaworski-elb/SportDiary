package com.jaworski.sportdiary.service.activity;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Service
public class ActivityService {

    private final List<Activity> activityList;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityList = activityRepository.getRepository();
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void delete(int id) {
        Iterator<Activity> iterator = activityList.iterator();
        Activity activity;
        while (iterator.hasNext()) {
            activity = iterator.next();
            if (activity.getId() == id) {
                iterator.remove();
            }
        }
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public int maxId() {
        return activityList.stream()
                .min((a1, a2) -> {
                    return a2.getId() - a1.getId();
                })
                .orElse(new Activity())
                .getId();
    }

    public List<Activity> sort(Comparator<Activity> comparator) {
        return activityList.stream()
                .sorted(comparator)
                .toList();
    }

    public Activity getActivity(int id) {
        return activityList.stream()
                .filter(activity -> activity.getId() == id)
                .findFirst()
                .orElse(new Activity());
    }

    public Activity update(int id, Activity activity) {
        Activity act = activityList.stream()
                .filter(activity1 -> activity1.getId() == id)
                .findFirst()
                .orElse(new Activity());
        int i = activityList.indexOf(act);
        activityList.set(i, activity);
        return activity;
    }
}
