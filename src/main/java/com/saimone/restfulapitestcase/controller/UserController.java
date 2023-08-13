package com.saimone.restfulapitestcase.controller;

import com.saimone.restfulapitestcase.model.User;
import com.saimone.restfulapitestcase.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private UserServiceImpl service;

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        return service.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id) {
        return service.updateUser(id);
    }
}
