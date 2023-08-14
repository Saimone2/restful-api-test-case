package com.saimone.restfulapitestcase.service;

import com.saimone.restfulapitestcase.exception.UserExistingEmailException;
import com.saimone.restfulapitestcase.exception.UserNotFoundException;
import com.saimone.restfulapitestcase.model.User;
import com.saimone.restfulapitestcase.repository.UserRepository;
import com.saimone.restfulapitestcase.response.ResponseHandler;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository repo;

    @Override
    public ResponseEntity<Object> saveUser(User user) {
        simulateDelay();
        if(repo.findByEmail(user.getEmail()).isPresent()) {
            logger.warn("IN saveUser - the user is trying to enter an already registered email: {}", user.getEmail());
            throw new UserExistingEmailException("This email is already registered");
        }
        Long id = repo.saveAndFlush(user).getId();
        logger.info("IN saveUser - the user successfully registered with id: {}", id);
        return ResponseHandler.responseBuilder("The user has been saved", HttpStatus.OK, id);
    }

    @Override
    public ResponseEntity<Object> getAllUsers() {
        simulateDelay();

        List<User> users = repo.findAll();
        logger.info("IN getAllUsers - a list of all users was passed");
        return ResponseHandler.responseBuilder("Information about all users was given", HttpStatus.OK, users);
    }

    @Override
    public ResponseEntity<Object> findUserById(Long id) {
        simulateDelay();
        User user = repo.findById(id).orElseThrow(() -> {
            logger.warn("IN findUserById - tried to get information about a non-existent user with id: {}", id);
            return new UserNotFoundException("User does not exist");
        });
        logger.info("IN findUserById - the user was passed with id: {}", id);
        return ResponseHandler.responseBuilder("Information about the user by id was given", HttpStatus.OK, user);
    }

    @Override
    public ResponseEntity<Object> updateUser(Long id) {
        simulateDelay();
        User user = repo.findById(id).orElseThrow(() -> {
            logger.warn("IN updateUser - tried to get information about a non-existent user with id: {}", id);
            return new UserNotFoundException("User does not exist");
        });
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
        logger.info("IN updateUser - status was changed for user with id: {}", id);
        return ResponseHandler.responseBuilder("User status has been changed", HttpStatus.OK, updateResponse);
    }

    private void simulateDelay() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}