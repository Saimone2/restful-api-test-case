package com.saimone.restfulapitestcase.service;

import com.saimone.restfulapitestcase.exception.UserExistingEmailException;
import com.saimone.restfulapitestcase.exception.UserNotFoundException;
import com.saimone.restfulapitestcase.logger.UserLogger;
import com.saimone.restfulapitestcase.model.User;
import com.saimone.restfulapitestcase.repository.UserRepository;
import com.saimone.restfulapitestcase.response.ResponseHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repo;

    @Override
    public ResponseEntity<Object> saveUser(User user) {
        simulateDelay();
        if(repo.findByEmail(user.getEmail()).isPresent()) {
            UserLogger.LOGGER.log(Level.WARNING, "IN saveUser - the user is trying to enter an already registered email: " + user.getEmail());
            throw new UserExistingEmailException("This email is already registered");
        }
        Long id = repo.saveAndFlush(user).getId();
        UserLogger.LOGGER.log(Level.INFO, "IN saveUser - the user successfully registered with id: " + id);
        return ResponseHandler.responseBuilder("The user has been saved", HttpStatus.OK, id);
    }

    @Override
    public ResponseEntity<Object> getAllUsers() {
        simulateDelay();

        List<User> users = repo.findAll();
        UserLogger.LOGGER.log(Level.INFO, "IN getAllUsers - a list of all users was passed");
        return ResponseHandler.responseBuilder("Information about all users was given", HttpStatus.OK, users);
    }

    @Override
    public ResponseEntity<Object> findUserById(Long id) {
        simulateDelay();
        User user = repo.findById(id).orElseThrow(() -> {
            UserLogger.LOGGER.log(Level.WARNING, "IN findUserById - tried to get information about a non-existent user with id: " + id);
            return new UserNotFoundException("User does not exist");
        });
        UserLogger.LOGGER.log(Level.INFO, "IN findUserById - the user was passed with id: " + id);
        return ResponseHandler.responseBuilder("Information about the user by id was given", HttpStatus.OK, user);
    }

    @Override
    public ResponseEntity<Object> updateUser(Long id) {
        simulateDelay();
        User user = repo.findById(id).orElseThrow(() -> {
            UserLogger.LOGGER.log(Level.WARNING, "IN updateUser - tried to get information about a non-existent user with id: " + id);
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
        UserLogger.LOGGER.log(Level.INFO, "IN updateUser - status was changed for user with id: " + id);
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
