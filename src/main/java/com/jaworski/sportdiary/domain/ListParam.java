package com.jaworski.sportdiary.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ListParam {

    private String sort = "";
    private Boolean isAscending = true;

}
