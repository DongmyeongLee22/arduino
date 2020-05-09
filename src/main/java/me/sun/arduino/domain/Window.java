package me.sun.arduino.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "windowState")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Window extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "window_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private WindowState windowState;

    public Window(WindowState windowState) {
        this.windowState = windowState;
    }
}


