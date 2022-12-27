package com.jaworski.sportdiary.config.security;

import com.jaworski.sportdiary.entity.UserEntity;
import com.jaworski.sportdiary.repository.UserEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserEntityPrincipalDetailService implements UserDetailsService {

    private UserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> byFirstName = userEntityRepository.findByFirstName(username);
        return new UserPrincipal(byFirstName.orElseThrow(() -> new IllegalArgumentException("User not found")));
    }
}
