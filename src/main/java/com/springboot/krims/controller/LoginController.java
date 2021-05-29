package com.springboot.krims.controller;

import com.springboot.krims.dto.DefaultResponse;
import com.springboot.krims.dto.Greeting;
import com.springboot.krims.dto.Login;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class LoginController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/hello")
    public Greeting hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/login")
    public DefaultResponse login(@RequestParam String username, String password) {
        return validate(username, password);
    }

    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public DefaultResponse login(@RequestBody Login login) {
        return validate(login.getUsername(), login.getPassword());
    }

    private DefaultResponse validate(String username, String password) {
        boolean isValid = username.equals("admin") && password.equals("admin");

        String status = isValid ? "success" : "error";
        String message = isValid ? "Login succeeded" : "Invalid Login. Please try again!";
        return new DefaultResponse(status, message);
    }
}
