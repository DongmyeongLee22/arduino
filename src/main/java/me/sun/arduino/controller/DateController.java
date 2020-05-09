package me.sun.arduino.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class DateController {

    @GetMapping("/date")
    public DateWrapper dateWrapper() {
        return new DateWrapper(LocalDateTime.now());
    }

    @AllArgsConstructor
    @Getter
    static class DateWrapper {
        private LocalDateTime localDateTime;
    }
}
