package com.saimone.restfulapitestcase.service;

import com.saimone.restfulapitestcase.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    Long save(User user);
    List<User> getAllUsers();
    User findById(Long id);
    Map<String, String> updateUser(Long id);
}
