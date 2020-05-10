package me.sun.arduino;

import lombok.RequiredArgsConstructor;
import me.sun.arduino.domain.MeasureValue;
import me.sun.arduino.domain.User;
import me.sun.arduino.repo.MeasureValueRepository;
import me.sun.arduino.repo.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ArduinoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArduinoApplication.class, args);
    }

    @Component
    @RequiredArgsConstructor
    static class MyRunner implements ApplicationRunner{

        private final UserRepository userRepository;
        private final MeasureValueRepository measureValueRepository;

        @Override
        public void run(ApplicationArguments args) throws Exception {

            Optional<User> findUser = userRepository.findByName("이혜은");

            User user = saveUserIfNotPresent(findUser);


            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                try {
                    newThreadWork(user, measureValueRepository);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        private void newThreadWork(User user, MeasureValueRepository measureValueRepository) throws InterruptedException {
            while(true){
                Random random = new Random();
                int temp = 10 + random.nextInt(20);
                int humidity = 30 + random.nextInt(10);
                int fineDust = 30 + random.nextInt(70);
                int isRainAB = random.nextInt(10);

                MeasureValue value = MeasureValue.builder()
                        .temperature(String.valueOf(temp))
                        .humidity(String.valueOf(humidity))
                        .fineDust(String.valueOf(fineDust))
                        .isRain(isRainAB > 9)
                        .user(user)
                        .build();

                measureValueRepository.save(value);

                TimeUnit.SECONDS.sleep(20);
            }
        }

        private User saveUserIfNotPresent(Optional<User> optionalUser) {
            if (!optionalUser.isPresent()) {
                User user = User.builder()
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
                userRepository.save(user);
                return user;
            }
            return optionalUser.get();
        }

    }

}
