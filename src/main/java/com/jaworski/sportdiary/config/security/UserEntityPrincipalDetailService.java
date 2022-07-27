package com.jaworski.sportdiary.config.security;

import com.jaworski.sportdiary.entity.UserEntity;
import com.jaworski.sportdiary.repository.UserEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserEntityPrincipalDetailService implements UserDetailsService {

    private UserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userEntityRepository.findByFirstName(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(userEntity);
    }
}
