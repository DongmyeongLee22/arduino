package me.sun.arduino.repo;

import me.sun.arduino.domain.MeasureValue;
import me.sun.arduino.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.awt.print.Pageable;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class MeasureValueRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MeasureValueRepository measureValueRepository;

    @Autowired
    EntityManager em;

    @Test
    void findValues() throws Exception{
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



        initValue(user);

        em.flush();
        em.clear();

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createDate"));

        Page<MeasureValue> result = measureValueRepository.findMeasureValueWithUserByUsername(user.getId(), pageRequest);
        result.getContent().stream().map(MeasureValue::getCreateDate).forEach(System.out::println);
        result.getContent().stream().map(MeasureValue::getUser).forEach(System.out::println);

    }

    private void initValue(User user) {
        Random random = new Random();

        for(int i = 0; i < 100; i++){
            int temp = random.nextInt(30);
            int humidity = 10 + random.nextInt(40);
            int fineDust = 30 + random.nextInt(70);
            int isRainAB = random.nextInt(10);

            MeasureValue value = MeasureValue.builder()
                    .temperature(String.valueOf(temp))
                    .humidity(String.valueOf(humidity))
                    .fineDust(String.valueOf(fineDust))
                    .isRain(isRainAB < 9)
                    .user(user)
                    .build();

            measureValueRepository.save(value);
        }
    }


}