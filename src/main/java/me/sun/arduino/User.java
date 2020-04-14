package me.sun.arduino;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
