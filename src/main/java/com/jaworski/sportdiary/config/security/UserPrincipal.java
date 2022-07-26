package com.jaworski.sportdiary.config.security;

import com.jaworski.sportdiary.domain.User;
import com.jaworski.sportdiary.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class UserPrincipal implements UserDetails {

    private final UserEntity userEntity;

    public UserPrincipal(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (userEntity.getAuthorities().isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        userEntity.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getFirstName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userEntity.isActive();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserPrincipal{");
        sb.append("userEntity=").append(userEntity).append(System.lineSeparator());
        sb.append(userEntity.getFirstName()).append(System.lineSeparator());
        sb.append(userEntity.getRoles().toString()).append(System.lineSeparator());
        sb.append(userEntity.getAuthorities().toString());
        return sb.toString();
    }

    public User getUser() {
        return new User(userEntity.getId(), userEntity.getFirstName(), userEntity.getFirstName(), userEntity.getEmail(),
                userEntity.getRoles().stream().reduce("", (a, b) -> a + "," + b));
    }

    public UUID getId() {
        return userEntity.getId();
    }
}
