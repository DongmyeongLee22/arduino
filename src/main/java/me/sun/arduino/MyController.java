package me.sun.arduino;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MyController {

    private final InformationRepository informationRepository;

    @GetMapping("/information")
    public String hello(@RequestParam String temp, @RequestParam String humi){
        log.info("information Get temperature{}, humidity{}", temp, humi);
        informationRepository.save(Information.builder().
                temperature(temp)
                .humidity(humi)
                .build());
        log.info("저장 성공");
        return "저장 성공";
    }

    @GetMapping("/")
    public String main(){
        return "메인 페이지";
    }
}
