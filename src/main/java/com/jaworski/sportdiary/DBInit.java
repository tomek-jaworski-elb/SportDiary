package com.jaworski.sportdiary;

import com.jaworski.sportdiary.config.security.UserEntityRepository;
import com.jaworski.sportdiary.entity.UserEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBInit implements CommandLineRunner {

    private PasswordEncoder passwordEncoder;
    private UserEntityRepository userEntityRepository;

    public DBInit(PasswordEncoder passwordEncoder, UserEntityRepository userEntityRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        userEntityRepository.deleteAll();

        UserEntity dan = new UserEntity("u", passwordEncoder.encode("u"), "USER", "");
        UserEntity admin = new UserEntity("admin", passwordEncoder.encode("admin"), "ADMIN,USER", "");
        UserEntity user = new UserEntity("user", passwordEncoder.encode("user"), "ADMIN", "");

        List<UserEntity> userEntities = List.of(dan, admin, user);
        userEntityRepository.saveAll(userEntities);

    }
}
