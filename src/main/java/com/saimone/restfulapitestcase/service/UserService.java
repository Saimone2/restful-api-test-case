package com.saimone.restfulapitestcase.service;

import com.saimone.restfulapitestcase.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<Object> saveUser(User user);
    ResponseEntity<Object> getAllUsers();
    ResponseEntity<Object> findUserById(Long id);
    ResponseEntity<Object> updateUser(Long id);
}
