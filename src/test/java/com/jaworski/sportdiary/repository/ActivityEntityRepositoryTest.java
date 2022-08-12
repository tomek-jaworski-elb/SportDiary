package com.jaworski.sportdiary.repository;

import com.jaworski.sportdiary.domain.enums.Sport;
import com.jaworski.sportdiary.domain.enums.Unit;
import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(activityEntityRepository).isNotNull();
    }

    @Test
    void findById_whenActivityExists_thenReturnActivity() {
        // given
        UserEntity userEntity = new UserEntity("test", "testtestes", "test", "test");
        UserEntity save = userEntityRepository.save(userEntity);
        assertThat(save).isNotNull();
        LocalDateTime now = LocalDateTime.now();
        ActivityEntity activityEntity = new ActivityEntity(now, now, now, 10L, 1D, Unit.KM, Sport.GYM, save);

        ActivityEntity save1 = activityEntityRepository.save(activityEntity);
        // when
        ActivityEntity foundActivity = activityEntityRepository.findById(save1.getId()).orElse(null);
        // then
        assertThat(save1).isNotNull();
        assertThat(foundActivity).isNotNull();
        assertNotNull(foundActivity);
        assertThat(foundActivity.getDateTime().truncatedTo(ChronoUnit.MILLIS)).isEqualTo(activityEntity.getDateTime().truncatedTo(ChronoUnit.MILLIS));
        assertThat(foundActivity.getDuration()).isEqualTo(activityEntity.getDuration());
        assertThat(foundActivity.getDistanceOf()).isEqualTo(activityEntity.getDistanceOf());
        assertThat(foundActivity.getUnit()).isEqualTo(activityEntity.getUnit());
        assertThat(foundActivity.getSport()).isEqualTo(activityEntity.getSport());
        assertThat(foundActivity.getUserEntity().getId()).isEqualTo(activityEntity.getUserEntity().getId());
    }

    @Test
    void shouldFindActivityById() {

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