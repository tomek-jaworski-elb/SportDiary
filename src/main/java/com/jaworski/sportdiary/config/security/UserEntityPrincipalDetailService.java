package com.jaworski.sportdiary.config.security;

import com.jaworski.sportdiary.entity.UserEntity;
import com.jaworski.sportdiary.entity.repository.UserEntityRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserEntityPrincipalDetailService implements UserDetailsService {

    private UserEntityRepository userEntityRepository;

    public UserEntityPrincipalDetailService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userEntityRepository.findByFirstName(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        UserPrincipal userPrincipal = new UserPrincipal(userEntity);
        return userPrincipal;
    }
}
