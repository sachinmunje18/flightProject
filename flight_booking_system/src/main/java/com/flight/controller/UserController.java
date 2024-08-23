// src/main/java/com/flight/controller/UserController.java
package com.flight.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flight.model.User;
import com.flight.service.UserService;
import com.flight.vo.UserVo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flight")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> saveUser(@RequestBody UserVo uservo) {
        User user = User.builder()
                .name(uservo.getName())
                .email(uservo.getEmail())
                .password(uservo.getPassword())
                .build();
        userService.registerMember(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserVo uservo) {
        User user = User.builder()
                        .email(uservo.getEmail())
                        .password(uservo.getPassword())
                        .build();

        User getUser = userService.EmailAndPassword(user.getEmail(), user.getPassword());

        if (getUser != null) {
            return ResponseEntity.ok("LOGIN SUCCESSFULLY!");
        } else {
            return ResponseEntity.status(401).body("INVALID login");
        }
    }
}