package com.jaworski.sportdiary.repository;

import com.jaworski.sportdiary.domain.enums.Sport;
import com.jaworski.sportdiary.domain.enums.Unit;
import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@SpringBootTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ActivityEntityRepositoryTest {

    @Autowired
    private ActivityEntityRepository activityEntityRepository;
    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(activityEntityRepository).isNotNull();
        assertThat(userEntityRepository).isNotNull();
    }

    @Test
    void saveUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("John");
        userEntity.setPassword(passwordEncoder.encode("password"));
        userEntity.setRoles("USER");
        userEntity.setEmail("test@wp.pl");
        UserEntity save = userEntityRepository.save(userEntity);
        assertThat(save).isNotNull();
        assertThat(save.getId()).isNotNull();
        assertThat(save.getFirstName()).isEqualTo("John");
        assertThat(passwordEncoder.matches("password", save.getPassword())).isTrue();
        assertThat(save.getEmail()).isEqualTo("test@wp.pl");
    }


    @Test
    void findById_whenActivityExists_thenReturnActivity() {
        // given
        UserEntity userEntity = new UserEntity("test", passwordEncoder.encode("test"), "USER", "");
        UserEntity user = userEntityRepository.save(userEntity);
        ActivityEntity activityEntity = new ActivityEntity(LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(),
                5L, 5D, Unit.KM, Sport.RUNNING, user);
        // when
        ActivityEntity activity = activityEntityRepository.save(activityEntity);
        ActivityEntity foundActivity = activityEntityRepository.findById(activity.getId()).orElse(null);
//        // then
        assertThat(activity).isNotNull();
        assertThat(foundActivity).isNotNull();
        assertNotNull(foundActivity);
        assertThat(foundActivity.getDateTime().truncatedTo(ChronoUnit.MILLIS))
                .isEqualTo(activityEntity.getDateTime().truncatedTo(ChronoUnit.MILLIS));
        assertThat(foundActivity.getDuration()).isEqualTo(activityEntity.getDuration());
        assertThat(foundActivity.getDistanceOf()).isEqualTo(activityEntity.getDistanceOf());
        assertThat(foundActivity.getUnit()).isEqualTo(activityEntity.getUnit());
        assertThat(foundActivity.getSport()).isEqualTo(activityEntity.getSport());
    }

    @Test
    void findById_whenActivityNotExists_thenReturnNull() {
        // given
        UUID id = UUID.randomUUID();
        // when
        Optional<ActivityEntity> foundActivity = activityEntityRepository.findById(id);
        // then
        assertThat(foundActivity).isEmpty();

    }

    @Test
    void testRegister() {
    }

    @Test
    void findById() {
        // given
        UUID activityId = UUID.randomUUID();
        ActivityEntity activityEntity = mock(ActivityEntity.class);
        ActivityEntityRepository mock = mock(ActivityEntityRepository.class);
        given(mock.findById(activityId)).willReturn(Optional.of(activityEntity));
        // when
        // then
        activityEntityRepository.findById(activityId);
        assertThat(mock.findById(activityId)).isNotNull();
        then(mock).should().findById(activityId);
        Optional<ActivityEntity> byId = activityEntityRepository.findById(activityId);
        assertThat(byId).isNotNull();
        assertThrows(NoSuchElementException.class, () -> byId.get().getUserEntity().getId());
        assertNotNull(byId);
    }
}