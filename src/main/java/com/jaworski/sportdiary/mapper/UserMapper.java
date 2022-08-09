package com.jaworski.sportdiary.mapper;

import com.jaworski.sportdiary.domain.User;
import com.jaworski.sportdiary.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserEntity toUserEntity(User user) {
        UserEntity userEntity = new UserEntity(user.getFirstName(), passwordEncoder.encode(user.getPassword()),
                "USER", "");
        userEntity.setEmail(user.getEmail());
        return userEntity;
    }

    public User toUser(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getFirstName(), "", userEntity.getEmail(),
                userEntity.getRoles().stream().reduce("", (s, r) -> s + "," + r));
    }

}
