package me.sun.arduino.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sun.arduino.domain.MeasureValue;
import me.sun.arduino.domain.User;
import me.sun.arduino.repo.MeasureValueRepository;
import me.sun.arduino.repo.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MyController {

    private final MeasureValueRepository measureValueRepository;
    private final UserRepository userRepository;

    @GetMapping("/information")
    public String hello(@RequestParam String temp, @RequestParam String humi){ // 아두이노의 온습도 값을 받아온다.

        // 로그를 찍는다(프린트)
        log.info("information Get temperature{}, humidity{}", temp, humi);

        User user = userRepository.findByName("이혜은").get();
        // 받아온 데이터로 하나의 객체를 만든다.
        MeasureValue measureValue = new MeasureValue(temp, humi, null, false, user);

        // 해당 객체를 JPA를 이용하여 저장한다.
        measureValueRepository.save(measureValue);

        // 로그를 찍는다.
        log.info("저장 성공");
        return "저장 성공";
    }

    @GetMapping("/")
    public String main(){
        return "메인 페이지";
    }
}
