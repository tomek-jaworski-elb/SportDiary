package com.jaworski.sportdiary;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.entity.UserEntity;
import com.jaworski.sportdiary.mapper.ActivityMapper;
import com.jaworski.sportdiary.repository.ActivityEntityRepository;
import com.jaworski.sportdiary.repository.ActivityRepository;
import com.jaworski.sportdiary.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DBInit implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserEntityRepository userEntityRepository;
    private final ActivityRepository activityRepository;
    private final ActivityEntityRepository activityEntityRepository;
    private final ActivityMapper activityMapper;

    @Override
    public void run(String... args) throws Exception {

        UserEntity dan = new UserEntity("u", passwordEncoder.encode("u"), "USER", "");
        UserEntity admin = new UserEntity("admin", passwordEncoder.encode("admin"), "ADMIN", "");
        UserEntity user = new UserEntity("user", passwordEncoder.encode("user"), "USER", "");

        List<UserEntity> userEntities = List.of(dan, admin, user);
//        userEntityRepository.saveAll(userEntities);

        List<Activity> activities = activityRepository.getActivities();
        System.out.println(activities);
        for (Activity act : activities) {
            act.setAddedAt(LocalDateTime.now());
            act.setLastModifiedAt(LocalDateTime.now());
            ActivityEntity activityEntity = activityMapper.ActivityToEntity(act);
//            activityEntityRepository.save(activityEntity);
        }
    }
}
