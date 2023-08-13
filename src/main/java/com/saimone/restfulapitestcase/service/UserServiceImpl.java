package com.saimone.restfulapitestcase.service;

import com.saimone.restfulapitestcase.exception.UserExistingEmailException;
import com.saimone.restfulapitestcase.exception.UserNotFoundException;
import com.saimone.restfulapitestcase.model.User;
import com.saimone.restfulapitestcase.repository.UserRepository;
import com.saimone.restfulapitestcase.response.ResponseHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repo;

    @Override
    public ResponseEntity<Object> save(User user) {
        if(repo.findByEmail(user.getEmail()).isPresent()) {
            throw new UserExistingEmailException("This email is already registered");
        }
        Long id = repo.saveAndFlush(user).getId();
        return ResponseHandler.responseBuilder("The user has been saved", HttpStatus.OK, id);
    }

    @Override
    public ResponseEntity<Object> getAllUsers() {
        return ResponseHandler.responseBuilder("Information about all users was given", HttpStatus.OK, repo.findAll());
    }

    @Override
    public ResponseEntity<Object> findById(Long id) {
        return ResponseHandler.responseBuilder("Information about the user by id was given", HttpStatus.OK, repo.findById(id).orElseThrow(() -> new UserNotFoundException("User does not exist")));
    }

    @Override
    public ResponseEntity<Object> updateUser(Long id) {
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
        return ResponseHandler.responseBuilder("User status has been changed", HttpStatus.OK, updateResponse);
    }
}
