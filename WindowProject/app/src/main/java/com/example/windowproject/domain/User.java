package com.example.windowproject.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class User implements Serializable {
    private String name;

    private boolean checkedTemp;
    private Integer closeWindowTemp;
    private Integer openWindowTemp;

    private boolean checkedHumidity;
    private Integer closeWindowHumidity;
    private Integer openWindowHumidity;

    private boolean checkedFineDust;
    private Integer closeWindowFineDust;
    private Integer openWindowFineDust;

}