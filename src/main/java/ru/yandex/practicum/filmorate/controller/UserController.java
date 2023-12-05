package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> usersList = new HashMap<>();
    private int generatedId = 1;

    @GetMapping
    public List<User> findAllUsers() {
        return new ArrayList<>(usersList.values());
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) throws ValidationException {
        validatingUser(user);
        user.setId(generatedId++);
        usersList.put(user.getId(), user);
        log.info("Пользователь добавлен в список!" + user.getId() + " " + user.getName() + " " + user.getLogin()
                + " " + user.getEmail() + " " + user.getBirthday());
        return user;
    }

    @PutMapping
    public User upadateUser(@Valid @RequestBody User user) throws ValidationException {
        validatingUser(user);
        if (usersList.containsKey(user.getId())) {
            usersList.put(user.getId(), user);
            log.info("Данные о пользователе были изменены: " + user.getId() + " " + user.getName() + " " + user.getLogin()
                    + " " + user.getEmail() + " " + user.getBirthday());
        } else {
            log.info("Данного пользователя нет в списке: " + user.getName());
            throw new ValidationException("Данного пользователя нет в списке: " + user.getName());
        }
        return user;
    }

    private void validatingUser(User user) throws ValidationException {
        if (user.getLogin().contains(" ")) {
            log.debug("Поля логин не может быть пустым или содержать пробелы: " + user.getLogin());
            throw new ValidationException("Поля логин не может быть пустым или содержать пробелы!");
        }
        if (user.getName() == null) {
            if (!user.getLogin().contains(" ")) {
                user.setName(user.getLogin());
            } else {
                log.debug("Поля логин не может быть пустым или содержать пробелы : " + user.getLogin());
                throw new ValidationException("Поля логин не может быть пустым или содержать пробелы!");
            }
        }
    }
}