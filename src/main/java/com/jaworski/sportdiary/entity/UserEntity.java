package com.jaworski.sportdiary.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "{valid.username.empty}")
    @Length(min = 3, max = 20, message = "{valid.username.length}")
    private String firstName;

    @NotNull(message = "{valid.username.empty}")
    @Length(min = 3, max = 20, message = "{valid.username.length}")
    private String lastName;

    @NotNull(message = "{valid.username.empty}")
    @Email(message = "{valid.email.empty}")
    private String email;

    @NotNull(message = "{valid.username.empty}")
    @Length(min = 3, message = "{valid.username.length}")
    private String password;

    @NotNull(message = "{valid.username.empty}")
    private String role;

    @OneToMany(mappedBy = "user")
    private Set<ActivityEntity> entitySet;
}
