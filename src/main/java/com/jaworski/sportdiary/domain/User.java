package com.jaworski.sportdiary.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class User {

    private UUID id;

    @NotNull(message = "{valid.username.empty}")
    @Length(min = 3, max = 20, message = "{valid.username.length}")
    private String firstName;

    @Length(max = 20, message = "{valid.username.length}")
    private String lastName;

    @NotBlank(message = "{valid.username.empty}")
    @Email(message = "{valid.email.empty}")
    private String email;

    @NotNull(message = "{valid.username.empty}")
    @Length(min = 3, message = "{valid.username.length}")
    private String password;

    //    @NotNull(message = "{valid.username.empty}")
    @Enumerated(EnumType.STRING)
    private String roles;

    public User(UUID id, String firstName, String lastName, String email, String roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }
}
