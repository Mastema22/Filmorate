package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> usersList = new HashMap<>();
    private int generatedId = 0;

    @GetMapping
    public List<User> findAllUsers() {
        return new ArrayList<>(usersList.values());
    }

    @PostMapping
    public User createUser(@RequestBody User user) throws ValidationException {
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            log.debug("Электронная почта не может быть пустой или не содержать @ :" + user.getEmail());
            throw new ValidationException("Электронная почта не может быть пустой или не содержать @ !");
        }
        if (user.getLogin() == null || user.getLogin().contains(" ")) {
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
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.debug("Дата рождения не может быть в будущем: " + user.getBirthday());
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        user.setId(++generatedId);
        usersList.put(user.getId(), user);
        log.info("Пользователь добавлен в список!" + user.getId() + " " + user.getName() + " " + user.getLogin()
                + " " + user.getEmail() + " " + user.getBirthday());
        return user;
    }

    @PutMapping
    public User upadateUser(@RequestBody User user) throws ValidationException {
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            log.debug("Электронная почта не может быть пустой или не содержать @ :" + user.getEmail());
            throw new ValidationException("Электронная почта не может быть пустой или не содержать @ !");
        }
        if (user.getLogin() == null || user.getLogin().contains(" ")) {
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
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.debug("Дата рождения не может быть в будущем: " + user.getBirthday());
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        usersList.forEach((key, value) -> {
            if (user.getId() == key) {
                usersList.replace(user.getId(), user);
                log.info("Данные о пользователе были изменены: " + user.getId() + " " + user.getName() + " " + user.getLogin()
                        + " " + user.getEmail() + " " + user.getBirthday());
            } else {
                try {
                    log.info("Данного пользователя нет в списке: " + user.getName());
                    throw new ValidationException("Данного пользователя нет в списке: " + user.getName());
                } catch (ValidationException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return user;
    }

}