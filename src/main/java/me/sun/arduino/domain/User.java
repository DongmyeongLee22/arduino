package me.sun.arduino.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@ToString(exclude = {"measureValues"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonIgnore
    private Long id;

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

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<MeasureValue> measureValues = new ArrayList<>();

    @Builder
    public User(String name, boolean checkedTemp, Integer closeWindowTemp, Integer openWindowTemp, boolean checkedHumidity, Integer closeWindowHumidity, Integer openWindowHumidity, boolean checkedFineDust, Integer closeWindowFineDust, Integer openWindowFineDust, List<MeasureValue> measureValues) {
        this.name = name;
        this.checkedTemp = checkedTemp;
        this.closeWindowTemp = closeWindowTemp;
        this.openWindowTemp = openWindowTemp;
        this.checkedHumidity = checkedHumidity;
        this.closeWindowHumidity = closeWindowHumidity;
        this.openWindowHumidity = openWindowHumidity;
        this.checkedFineDust = checkedFineDust;
        this.closeWindowFineDust = closeWindowFineDust;
        this.openWindowFineDust = openWindowFineDust;
        this.measureValues = measureValues;
    }

    public void update(User newUser) {
        this.checkedTemp = newUser.checkedTemp;
        this.closeWindowTemp = newUser.closeWindowTemp;
        this.openWindowTemp = newUser.openWindowTemp;
        this.checkedHumidity = newUser.checkedHumidity;
        this.closeWindowHumidity = newUser.closeWindowHumidity;
        this.openWindowHumidity = newUser.openWindowHumidity;
        this.checkedFineDust = newUser.checkedFineDust;
        this.closeWindowFineDust = newUser.closeWindowFineDust;
        this.openWindowFineDust = newUser.openWindowFineDust;
    }
}
