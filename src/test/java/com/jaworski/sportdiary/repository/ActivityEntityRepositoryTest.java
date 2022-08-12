package com.jaworski.sportdiary.repository;

import com.jaworski.sportdiary.entity.ActivityEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@SpringBootTest
class ActivityEntityRepositoryTest {

    @Autowired
    private ActivityEntityRepository activityEntityRepository;

    @Test
    void injectedComponentsAreNotNull() {
        assertNotNull(activityEntityRepository);
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
        assertThrows(NoSuchElementException.class, () ->
                byId.get().getUserEntity().getId());
        assertNotNull(byId);
    }
}