package me.sun.arduino.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

@RestController
@RequiredArgsConstructor
public class DateController {

    private Random random = new Random();
    @GetMapping("/date")
    public Text dateWrapper(@RequestParam(name = "test", defaultValue = "hi") String hi) {
        int random = this.random.nextInt(10);
        return random < 5 ? new Text("열림") : new Text("닫힘");
    }

    @AllArgsConstructor
    @Getter
    static class DateWrapper {
        private LocalDateTime localDateTime;
        private LocalDateTime localDateTime2;
    }

    @AllArgsConstructor
    @Getter
    static class Text {
        String state;
    }
}
