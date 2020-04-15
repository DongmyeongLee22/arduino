package me.sun.arduino.controller;

import lombok.RequiredArgsConstructor;
import me.sun.arduino.domain.User;
import me.sun.arduino.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public User findMember(@RequestParam String name){
        return userService.findUser(name);
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User newUser){
        return userService.updateUser(newUser);
    }
}
