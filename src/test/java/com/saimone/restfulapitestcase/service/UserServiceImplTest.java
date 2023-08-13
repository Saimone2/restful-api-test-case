package com.saimone.restfulapitestcase.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.saimone.restfulapitestcase.exception.UserExistingEmailException;
import com.saimone.restfulapitestcase.model.User;
import com.saimone.restfulapitestcase.repository.UserRepository;

import java.util.*;

import com.saimone.restfulapitestcase.response.ResponseHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Test
    void testSave() {
        // given
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setId(1L);
        user.setImageUrl("");
        user.setName("Name");
        user.setStatus("Status");
        user.setSurname("Doe");
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setEmail("user@gmail.com");
        user2.setId(1L);
        user2.setImageUrl("");
        user2.setName("Name");
        user2.setStatus("Status");
        user2.setSurname("Doe");

        // when
        when(userRepository.findByEmail(Mockito.any())).thenReturn(ofResult);

        // then
        assertThrows(UserExistingEmailException.class, () -> userService.saveUser(user2));
        verify(userRepository).findByEmail(Mockito.any());
        verify(userRepository).saveAndFlush(user);
    }

    @Test
    void testGetAllUsers() {
        // when
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<Object> actualAllUsers = userService.getAllUsers();

        // then
        assertEquals(3, ((Map<String, Object>) Objects.requireNonNull(actualAllUsers.getBody())).size());
        assertTrue(actualAllUsers.hasBody());
        assertTrue(actualAllUsers.getHeaders().isEmpty());
        assertEquals(200, actualAllUsers.getStatusCodeValue());
        verify(userRepository).findAll();
    }

    @Test
    void testFindById() {
        // given
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setId(1L);
        user.setImageUrl("");
        user.setName("Name");
        user.setStatus("Status");
        user.setSurname("Doe");
        Optional<User> ofResult = Optional.of(user);

        // when
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ResponseEntity<Object> actualFindByIdResult = userService.findUserById(1L);

        // then
        assertEquals(3, ((Map<String, Object>) Objects.requireNonNull(actualFindByIdResult.getBody())).size());
        assertTrue(actualFindByIdResult.hasBody());
        assertTrue(actualFindByIdResult.getHeaders().isEmpty());
        assertEquals(200, actualFindByIdResult.getStatusCodeValue());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testUpdateUser() {
        // given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setStatus("Offline");

        // when
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        ResponseEntity<Object> response = userService.updateUser(userId);

        // then
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);

        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("id", userId.toString());
        expectedResponse.put("previous status", "Offline");
        expectedResponse.put("current status", "Online");

        ResponseEntity<Object> expected = ResponseHandler.responseBuilder("User status has been changed", HttpStatus.OK, expectedResponse);
        assertEquals(expected, response);
    }
}