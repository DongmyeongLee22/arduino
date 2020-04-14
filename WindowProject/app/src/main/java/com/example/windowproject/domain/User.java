package com.example.windowproject.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("checkedTemp", String.valueOf(checkedTemp));
        map.put("closeWindowTemp", String.valueOf(closeWindowTemp));
        map.put("openWindowTemp", String.valueOf(openWindowTemp));
        map.put("checkedHumidity", String.valueOf(checkedHumidity));
        map.put("closeWindowHumidity", String.valueOf(closeWindowHumidity));
        map.put("openWindowHumidity", String.valueOf(openWindowHumidity));
        map.put("checkedFineDust", String.valueOf(checkedFineDust));
        map.put("closeWindowFineDust", String.valueOf(closeWindowFineDust));
        map.put("openWindowFineDust", String.valueOf(openWindowFineDust));
        return map;
    }
}