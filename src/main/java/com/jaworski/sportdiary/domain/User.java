package com.jaworski.sportdiary.domain;

import com.jaworski.sportdiary.domain.enums.Role;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class User {
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
    @Enumerated(EnumType.STRING)
    private Role role;

}
