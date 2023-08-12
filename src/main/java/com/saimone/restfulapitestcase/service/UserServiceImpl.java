package com.saimone.restfulapitestcase.service;

import com.saimone.restfulapitestcase.exception.UserNotFoundException;
import com.saimone.restfulapitestcase.model.User;
import com.saimone.restfulapitestcase.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
        return repo.findById(id).orElseThrow(() -> new UserNotFoundException("User does not exist"));
    }

    @Override
    public Map<String, String> updateUser(Long id) {
        User user = repo.findById(id).orElseThrow(() -> new UserNotFoundException("User does not exist"));
        Map<String, String> updateResponse = new HashMap<>();
        updateResponse.put("id", user.getId().toString());
        updateResponse.put("previous status", user.getStatus());
        if(user.getStatus().equalsIgnoreCase("Offline")) {
            user.setStatus("Online");
        } else {
            user.setStatus("Offline");
        }
        updateResponse.put("current status", user.getStatus());
        repo.save(user);
        return updateResponse;
    }
}
