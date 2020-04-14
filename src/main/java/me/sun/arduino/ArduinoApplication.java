package me.sun.arduino;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ArduinoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArduinoApplication.class, args);
    }

    @Component
    @RequiredArgsConstructor
    static class MyRunenr implements ApplicationRunner{

        private final MemberRepository memberRepository;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            Member member = Member.builder()
                    .name("이혜은")
                    .checkedTemp(true)
                    .closeWindowTemp(0)
                    .openWindowTemp(20)
                    .checkedHumidity(false)
                    .closeWindowHumidity(0)
                    .openWindowHumidity(50)
                    .checkedFineDust(false)
                    .openWindowFineDust(0)
                    .closeWindowFineDust(50)
                    .build();
            memberRepository.save(member);
        }
    }

}
