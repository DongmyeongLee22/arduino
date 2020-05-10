package me.sun.arduino.service;

import lombok.RequiredArgsConstructor;
import me.sun.arduino.domain.MeasureValue;
import me.sun.arduino.domain.User;
import me.sun.arduino.repo.MeasureValueRepository;
import me.sun.arduino.repo.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MeasureValueService {

    private final MeasureValueRepository measureValueRepository;
    private final UserService userService;


    public List<MeasureValue> findMeasureValues(String username){
        User user = userService.findUser(username);
        Page<MeasureValue> values = measureValueRepository.findMeasureValueWithUserByUsername(user.getId(),
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createDate")));
        return values.getContent();
    }

    public MeasureValue findLatestValue(Long userId) {
        Page<MeasureValue> values = measureValueRepository
            .findMeasureValueWithUserByUsername(userId, PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "createDate")));
        return values.getContent().get(0);
    }

    public void save(MeasureValue measureValue) {
        User user = userService.findUser("이혜은");
        measureValue.setUser(user);
        measureValueRepository.save(measureValue);
    }
}
