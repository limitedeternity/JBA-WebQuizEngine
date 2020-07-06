package com.limitedeternity.engine.controllers;

import com.limitedeternity.engine.models.UserModel;
import com.limitedeternity.engine.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/register")
public class UserController {

    @Autowired
    private UserRepository users;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public UserModel registerUser(@Valid @RequestBody UserModel user) {
        Optional<UserModel> foundUser = users.findByEmail(user.getEmail());
        if (foundUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSolvedQuizzes(new ArrayList<>());
        return users.save(user);
    }
}
