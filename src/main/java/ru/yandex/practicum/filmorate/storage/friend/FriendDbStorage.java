package ru.yandex.practicum.filmorate.storage.friend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.List;


@Slf4j
@Component
public class FriendDbStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserStorage userStorage;

    public FriendDbStorage(JdbcTemplate jdbcTemplate, @Qualifier("userDbStorage") UserStorage userStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.userStorage = userStorage;
    }

    public void addFriend(int userId, int friendId) {
        User user = userStorage.findById(userId);
        User friend = userStorage.findById(friendId);
        if ((user != null) && (friend != null)) {
            String sqlQuery1 = "INSERT INTO friends (user_id, friend_id, isAccepted) VALUES (?, ?, ?)";
            jdbcTemplate.update(sqlQuery1, userId, friendId, false);
            log.info(friend.getName() + " добавлен в друзья " + user.getName());
        }
    }


    public void removeFriend(int userId, int friendId) {
        User user = userStorage.findById(userId);
        User friend = userStorage.findById(friendId);
        if ((user != null) && (friend != null)) {
            String sqlQuery = "DELETE friends WHERE user_id = ? AND friend_id =?";
            log.info(friend.getName() + " удален из друзей " + user.getName());
            jdbcTemplate.update(sqlQuery, userId, friendId);
        }
    }

    public List<User> getFriends(int userId) {
        User user = userStorage.findById(userId);
        if (user != null) {
            String sqlQuery = "SELECT f.friend_id, u.email ,u.login, u.user_name, u.birthday FROM friends f" +
                    " INNER JOIN USERS u ON f.friend_id  = u.user_id WHERE f.user_id = ?";
            log.info("Нашел друга с Id ={}", userId);
            return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> new User(
                            rs.getInt("friend_id"),
                            rs.getString("email"),
                            rs.getString("login"),
                            rs.getString("user_name"),
                            rs.getDate("birthday").toLocalDate(),
                            new HashSet<>()),
                    userId);
        } else
            throw new UserNotFoundException("Пользователя с Id = " + userId + " нет!");

    }


}
