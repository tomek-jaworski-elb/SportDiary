package com.jaworski.sportdiary.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "sports")
public class SportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "{valid.sport.empty}")
    private String name;

    @OneToMany(mappedBy = "sport")
    private Set<ActivityEntity> activitySet;
}
