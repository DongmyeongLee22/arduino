package me.sun.arduino;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/user")
    public User findMember(@RequestParam String name){
        User user = userRepository.findByName(name).get();
        System.out.println(user);
        return user;
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User newUser){
        User user = userRepository.findByName(newUser.getName()).get();
        user.update(newUser);
        return user;
    }
}
