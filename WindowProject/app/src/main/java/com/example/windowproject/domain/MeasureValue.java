package com.example.windowproject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
public class MeasureValue{

    String createDate;
    String temperature;
    String humidity;
    String fineDust;
    boolean isRain;
}

