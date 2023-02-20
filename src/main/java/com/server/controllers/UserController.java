package com.server.controllers;

import com.server.entities.User;
import com.server.repositories.UserRepository;
import com.server.repositories.projections.LoginResultProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("5eTools/api/user")
@CrossOrigin(origins = "https://localhost:3000")
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User userLogin) {
        LoginResultProjection result = userRepository.findByUsername(userLogin.getUsername());
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("username");
        } else if (!result.getPassword().equals(userLogin.getPassword())) {
            return ResponseEntity.badRequest().body("password");
        }

        return ResponseEntity.ok(result);
    }
}
