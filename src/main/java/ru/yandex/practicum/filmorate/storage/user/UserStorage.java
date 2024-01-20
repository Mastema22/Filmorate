package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
public interface UserStorage {

    User createUser(User user) throws ValidationException;

    User removeUser(User user);

    User upadateUser(User user) throws ValidationException;

    List<User> findAllUsers();

    User findById(Integer id);
}
