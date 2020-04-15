package me.sun.arduino.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BaseEntityTest {

    @Test
    void temp() throws Exception{
        LocalDateTime of = LocalDateTime.of(2020, 1, 1, 4, 2, 2, 3);
        System.out.println(of);

        System.out.println(of.toString().substring(0, 16));


    }

}