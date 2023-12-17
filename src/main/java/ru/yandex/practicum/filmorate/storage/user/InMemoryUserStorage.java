package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> usersList = new HashMap<>();
    private int generatedId = 1;

    @Override
    public User createUser(User user) throws ValidationException {
        validatingUser(user);
        user.setId(generatedId++);
        usersList.put(user.getId(), user);
        log.info("Пользователь добавлен в список!" + user.getId() + " " + user.getName() + " " + user.getLogin()
                + " " + user.getEmail() + " " + user.getBirthday());
        return user;
    }

    @Override
    public User removeUser(User user) {
        usersList.remove(user.getId());
        log.info("Пользователь удален из списка!" + user.getId() + " " + user.getName() + " " + user.getLogin()
                + " " + user.getEmail() + " " + user.getBirthday());
        return user;
    }

    @Override
    public User upadateUser(User user) throws UserNotFoundException, ValidationException {
        validatingUser(user);
        if (usersList.containsKey(user.getId())) {
            usersList.put(user.getId(), user);
            log.info("Данные о пользователе были изменены: " + user.getId() + " " + user.getName() + " " + user.getLogin()
                    + " " + user.getEmail() + " " + user.getBirthday());
        } else {
            log.info("Данного пользователя нет в списке: " + user.getName());
            throw new UserNotFoundException("Данного пользователя нет в списке: " + user.getName() + " " + user.getId());
        }
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        log.info("Список всех пользователей");
        return new ArrayList<>(usersList.values());
    }

    public User findById(Integer id) {
        if (usersList.containsKey(id)) {
            return usersList.get(id);
        }
        return null;
    }

    private void validatingUser(User user) throws ValidationException {
        if (user.getLogin().contains(" ")) {
            log.debug("Поля логин не может быть пустым или содержать пробелы: " + user.getLogin());
            throw new ValidationException("Поля логин не может быть пустым или содержать пробелы!");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            if (!user.getLogin().contains(" ")) {
                user.setName(user.getLogin());
            } else {
                log.debug("Поля логин не может быть пустым или содержать пробелы : " + user.getLogin());
                throw new ValidationException("Поля логин не может быть пустым или содержать пробелы!");
            }
        }
    }
}
