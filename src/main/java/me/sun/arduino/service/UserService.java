package me.sun.arduino.service;

import lombok.RequiredArgsConstructor;
import me.sun.arduino.domain.User;
import me.sun.arduino.repo.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User updateUser(User newUser){
        User user = userRepository.findByName(newUser.getName()).get();
        user.update(newUser);
        return user;
    }

    public User findUser(String name) {
        return userRepository.findByName(name).get();
    }
}
