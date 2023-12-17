package ru.yandex.practicum.filmorate.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) throws ValidationException {
        return userService.createUser(user);
    }

    @PutMapping
    public User upadateUser(@Valid @RequestBody User user) throws ValidationException {
        return userService.upadateUser(user);
    }

    @GetMapping("/{id}")
    public User findUserById(@NonNull @PathVariable Integer id) {
        return userService.findUserById(id);
    }

    @DeleteMapping
    public User removeUser(@Valid @RequestBody User user) {
        return userService.removeUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addToFriendList(@NonNull @PathVariable("id") Integer id, @NonNull @PathVariable("friendId") Integer friendId) {
        return userService.addToFriendList(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFromFriendList(@NonNull @PathVariable("id") Integer id, @NonNull @PathVariable("friendId") Integer friendId) {
        return userService.removeFromFriendList(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> friendsListForUser(@NonNull @PathVariable("id") Integer id) {
        return userService.friendsListForUser(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> friendsListCommonOtherUsers(@NonNull @PathVariable("id") Integer id, @NonNull @PathVariable("otherId") Integer otherId) {
        return userService.friendsListCommonOtherUsers(id, otherId);

    }
}