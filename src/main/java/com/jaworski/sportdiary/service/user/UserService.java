package com.jaworski.sportdiary.service.user;

import com.jaworski.sportdiary.domain.User;
import com.jaworski.sportdiary.entity.UserEntity;
import com.jaworski.sportdiary.mapper.UserMapper;
import com.jaworski.sportdiary.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User getUserById(UUID id) {
        UserEntity userEntity = userEntityRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return userMapper.toUser(userEntity);
    }

    public List<User> getUserList() {
        List<UserEntity> userEntities = userEntityRepository.findAll();
        return userEntities.stream().map(userMapper::toUser).toList();
    }

    public User addUser(User user) {
        UserEntity userEntity = userMapper.toUserEntity(user);
        userEntity = userEntityRepository.save(userEntity);
        return userMapper.toUser(userEntity);
    }
}
