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
@Table(name = "units")
public class UnitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "{valid.sport.empty}")
    private String name;

    @OneToMany(mappedBy = "unit",fetch = FetchType.LAZY)
    private Set<ActivityEntity> activitySet;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UnitEntity{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
