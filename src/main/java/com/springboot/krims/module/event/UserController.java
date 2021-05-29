package com.springboot.krims.module.event;

import com.springboot.krims.dto.User;
import com.springboot.krims.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(value = "/krims", produces = "application/json")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(path = "/user", consumes = "application/json")
    public @ResponseBody
    String addNewUser(@RequestBody User user) {
        userRepository.save(user);
        return "User added";
    }
}
