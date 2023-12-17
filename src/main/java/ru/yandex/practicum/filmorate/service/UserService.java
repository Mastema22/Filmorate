package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public List<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    public User createUser(User user) throws ValidationException {
        log.info("Пользователь создан: " + user.getName());
        return userStorage.createUser(user);
    }

    public User upadateUser(User user) throws ValidationException {
        log.info("Данные пользователя обновленны: " + user.getName());
        return userStorage.upadateUser(user);
    }

    public User findUserById(Integer id) {
        User user = userStorage.findById(id);
        if (user == null) {
            log.warn("Пользователь с " + id + " не найден!");
            throw new UserNotFoundException("Пользователь не найден");
        }
        return user;
    }

    public User removeUser(User user) {
        log.info("Пользователь удален: " + user.getName());
        return userStorage.removeUser(user);
    }

    public User addToFriendList(Integer id, Integer friendId) {
        User user = findUserById(id);
        User friend = findUserById(friendId);
        if (user != null && friend != null) {
            user.getFriends().add(friendId);
            friend.getFriends().add(id);
            log.info("Пользователь " + friend.getName() + " добавлен в друзья к " + user.getName());
        }

        return user;
    }

    public User removeFromFriendList(Integer id, Integer friendId) {
        User user = findUserById(id);
        User friend = findUserById(friendId);
        if (user != null && friend != null) {
            user.getFriends().remove(friendId);
            friend.getFriends().remove(id);
            log.info("Пользователь " + friend.getName() + " удален из списка друзей пользователя " + user.getName());
        }
        return user;
    }

    public List<User> friendsListForUser(Integer userId) {
        List<User> result = new ArrayList<>();
        User user = findUserById(userId);
        if (user != null) {
            for (Integer id : user.getFriends()) {
                result.add(userStorage.findById(id));
            }
        }
        return result;
    }

    public List<User> friendsListCommonOtherUsers(Integer userId, Integer otherId) {
        List<User> result = new ArrayList<>();
        User user = findUserById(userId);
        User otherUser = findUserById(otherId);
        if (user != null && otherUser != null) {
            Set<Integer> usersFriends = new HashSet<>(user.getFriends());
            Set<Integer> otherUsersFriends = new HashSet<>(otherUser.getFriends());
            usersFriends.retainAll(otherUsersFriends);
            usersFriends.forEach(id -> result.add(findUserById(id)));
        }
        return result;
    }
}