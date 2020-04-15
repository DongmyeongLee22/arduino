package me.sun.arduino.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@ToString(exclude = {"user"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeasureValue extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "measure_id")
    @JsonIgnore
    private Long id;

    private String temperature;
    private String humidity;
    private String fineDust;

    private boolean isRain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Builder
    public MeasureValue(String temperature, String humidity, String fineDust, boolean isRain, User user) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.fineDust = fineDust;
        this.isRain = isRain;
        this.user = user;
    }
}
