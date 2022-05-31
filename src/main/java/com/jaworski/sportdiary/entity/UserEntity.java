package com.jaworski.sportdiary.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "{valid.username.empty}")
    @Length(min = 1, max = 20, message = "{valid.username.length}")
    private String firstName;

//    @NotNull(message = "{valid.username.empty}")
//    @Length(min = 3, max = 20, message = "{valid.username.length}")
//    private String lastName;

    //    @NotNull(message = "{valid.username.empty}")
    @Email(message = "{valid.email.empty}")
    private String email;

    @NotNull(message = "{valid.username.empty}")
    @Length(min = 2, message = "{valid.username.length}")
    private String password;

    @NotNull(message = "{valid.username.empty}")
    private String roles = "";

    private boolean isActive;

    @NotNull(message = "{valid.username.empty}")
    private String authorities = "";

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<ActivityEntity> entitySet;

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
