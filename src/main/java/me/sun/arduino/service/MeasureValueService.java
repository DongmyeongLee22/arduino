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
    private final UserRepository userRepository;


    public List<MeasureValue> findMeasureValues(String username){
        User user = userRepository.findByName(username).get();
        Page<MeasureValue> values = measureValueRepository.findMeasureValueWithUserByUsername(user.getId(),
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createDate")));
        return values.getContent();
    }

}
