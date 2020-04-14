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

}
