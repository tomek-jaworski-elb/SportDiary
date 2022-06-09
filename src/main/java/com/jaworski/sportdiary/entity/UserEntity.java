package com.jaworski.sportdiary.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = UserEntity.TABLE_NAME)
public class UserEntity {

    public static final String TABLE_NAME = "user";
    public static final String COLUMN_PREFIX = "u_";

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = COLUMN_PREFIX + "id")
    private UUID id;

    @NotNull(message = "{valid.username.empty}")
    @Length(min = 1, max = 20, message = "{valid.username.length}")
    @Column(name = COLUMN_PREFIX + "first_name")
    private String firstName;

//    @NotNull(message = "{valid.username.empty}")
//    @Length(min = 3, max = 20, message = "{valid.username.length}")
//    private String lastName;

    //    @NotNull(message = "{valid.username.empty}")
    @Email(message = "{valid.email.empty}")
    @Column(name = COLUMN_PREFIX + "email")
    private String email;

    @NotNull(message = "{valid.username.empty}")
    @Length(min = 2, message = "{valid.username.length}")
    @Column(name = COLUMN_PREFIX + "password")
    private String password;

    @NotNull(message = "{valid.username.empty}")
    @Column(name = COLUMN_PREFIX + "role")
    private String roles = "";

    @Column(name = COLUMN_PREFIX + "is_active")
    private boolean isActive;

    @NotNull(message = "{valid.username.empty}")
    @Column(name = COLUMN_PREFIX + "authorities")
    private String authorities = "";

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<ActivityEntity> activityEntityList = new ArrayList<>();

    public List<String> getRoles() {
        return Arrays.asList(roles.split(","));
    }

    public List<String> getAuthorities() {
        return Arrays.asList(authorities.split(","));
    }

    public UserEntity(String firstName, String password, String roles, String authorities) {
        this.firstName = firstName;
        this.password = password;
        this.roles = roles;
        this.authorities = authorities;
        this.isActive = true;
    }
}
