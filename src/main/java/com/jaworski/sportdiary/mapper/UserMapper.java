package com.jaworski.sportdiary.mapper;

import com.jaworski.sportdiary.domain.User;
import com.jaworski.sportdiary.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserEntity mapToUserEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(user.getRole().toString());
        return userEntity;
    }

}
