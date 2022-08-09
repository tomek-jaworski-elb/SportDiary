package com.jaworski.sportdiary.mapper;

import com.jaworski.sportdiary.domain.User;
import com.jaworski.sportdiary.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void toUserEntity_throwsIllegalArgumentException_givenUserDefaultConstructor() {
        User user = new User();
        assertThat(user).isNotNull();
        assertThat(user.getFirstName()).isNull();
        assertThat(user.getLastName()).isNull();
        assertThat(user.getEmail()).isNull();
        assertThat(user.getPassword()).isNull();
        assertThat(user.getRoles()).isNull();
        assertThrows(IllegalArgumentException.class, () -> userMapper.toUserEntity(user));
    }


    @Test
    void toUserEntity_returnsUserEntity_givenUser() {
        // given
        User user = new User();
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        user.setEmail("toja@wp.pl");
        user.setPassword("haslo");
        user.setRoles("USER");
        // when
        UserEntity userEntity = userMapper.toUserEntity(user);
        // then
        assertThat(userEntity.getId()).isNull();
        assertThat(userEntity.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userEntity.getEmail()).isEqualTo(user.getEmail());
        assertThat(passwordEncoder.matches(user.getPassword(), userEntity.getPassword())).isTrue();
        assertThat(userEntity.getRoles().stream().findFirst().orElse("")).isEqualTo(user.getRoles());
    }

    @Test
    void toUser_returnsUser_givenUserEntity() {
        // given
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        userEntity.setFirstName("Jan");
        userEntity.setEmail("toja@wp.pl");
        userEntity.setPassword(passwordEncoder.encode("haslo"));
        userEntity.setRoles("USER");
        userEntity.setActive(true);
        // when
        User user = userMapper.toUser(userEntity);
        // then
        assertThat(user.getId()).isEqualTo(userEntity.getId());
        assertThat(user.getFirstName()).isEqualTo(userEntity.getFirstName());
        assertThat(user.getEmail()).isEqualTo(userEntity.getEmail());
        assertThat(user.getRoles()).isEqualTo(userEntity.getRoles().stream().reduce("", (s, r) -> s + "," + r));
        assertThat(user.getPassword()).isEqualTo(userEntity.getPassword());
    }
}