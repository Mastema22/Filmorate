package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;
    private final FriendDbStorage friendDbStorage;

    public UserService(@Qualifier("userDbStorage") UserStorage userStorage, FriendDbStorage friendDbStorage) {
        this.userStorage = userStorage;
        this.friendDbStorage = friendDbStorage;
    }

    public List<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    public User createUser(User user) throws ValidationException {
        return userStorage.createUser(user);
    }

    public User upadateUser(User user) throws ValidationException {
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
        return userStorage.removeUser(user);
    }

    public void addToFriendList(Integer userId, Integer friendId) {
        if (Objects.equals(userId, friendId)) {
            throw new ValidationException("Нельзя добавить самого себя в друзья!");
        }
        friendDbStorage.addFriend(userId, friendId);
    }


    public void removeFromFriendList(Integer userId, Integer friendId) {
        if (userId == friendId) {
            throw new ValidationException("Нельзя удалить самого себя из друзей!");
        }
        friendDbStorage.removeFriend(userId, friendId);
    }

    public List<User> friendsListForUser(Integer userId) {
        return friendDbStorage.getFriends(userId);
    }

    public List<User> friendsListCommonOtherUsers(Integer userId, Integer otherId) {
        Set<User> result = null;
        User user = userStorage.findById(userId);
        User otherUser = userStorage.findById(otherId);
        if (user != null && otherUser != null) {
            result = new HashSet<>(friendDbStorage.getFriends(userId));
            result.retainAll(friendDbStorage.getFriends(otherId));
        }
        return new ArrayList<User>(result);
    }
}