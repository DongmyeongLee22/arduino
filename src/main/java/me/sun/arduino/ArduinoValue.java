package me.sun.arduino;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArduinoValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String temperature;
    private String humidity;
    private String findDust;

    private boolean isRain;

    @Builder
    public ArduinoValue(String temperature, String humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }
}
