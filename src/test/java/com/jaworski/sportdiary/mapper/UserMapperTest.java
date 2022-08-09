package com.jaworski.sportdiary.mapper;

import com.jaworski.sportdiary.domain.User;
import com.jaworski.sportdiary.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    void toUserEntity_returnsUserEntity_givenUser() {
        User user = new User();
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        user.setEmail("toja@wp.pl");
        user.setPassword("haslo");
        user.setRoles("USER");
        UserEntity userEntity = userMapper.toUserEntity(user);
        assertThat(userEntity.getId()).isNull();
        assertThat(userEntity.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userEntity.getEmail()).isEqualTo(user.getEmail());
        assertThat(passwordEncoder.matches(user.getPassword(), userEntity.getPassword())).isTrue();
        assertThat(userEntity.getRoles().stream().findFirst().orElse("")).isEqualTo(user.getRoles());
    }

    @Test
    void toUser() {

    }
}