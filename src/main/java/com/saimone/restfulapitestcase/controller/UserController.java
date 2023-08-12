package com.saimone.restfulapitestcase.controller;

import com.saimone.restfulapitestcase.model.User;
import com.saimone.restfulapitestcase.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private UserServiceImpl service;

    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Long addUser(@RequestBody User user) {
        return service.save(user);
    }

    @PutMapping("/{id}")
    public Map<String, String> updateUser(@PathVariable Long id) {
        return service.updateUser(id);
    }
}
