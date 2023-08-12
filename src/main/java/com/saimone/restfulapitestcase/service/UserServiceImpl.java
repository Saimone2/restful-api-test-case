package com.saimone.restfulapitestcase.service;

import com.saimone.restfulapitestcase.model.User;
import com.saimone.restfulapitestcase.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repo;

    @Override
    public Long save(User user) {
        return repo.saveAndFlush(user).getId();
    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public User findById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    @Override
    public Map<String, String> updateUser(Long id) {
        return null;
    }
}
