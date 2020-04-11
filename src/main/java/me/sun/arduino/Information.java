package me.sun.arduino;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String temperature;

    private String humidity;

    @Builder
    public Information(String temperature, String humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }
}
