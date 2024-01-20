package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
@Slf4j
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User createUser(User user) {
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        Map<String, Object> params = Map.of(
                "user_name", user.getName(),
                "email", user.getEmail(),
                "login", user.getLogin(),
                "birthday", user.getBirthday());
        Number id = simpleJdbcInsert.executeAndReturnKey(params).intValue();
        user.setId(id.intValue());
        log.info("Добавлен новый пользователь с Id={}", user.getId());
        return user;
    }

    @Override
    public User removeUser(User user) {
        String sqlQuery = "DELETE FROM users WHERE users_id = ?";
        if (jdbcTemplate.update(sqlQuery, user.getId()) == 0) {
            throw new UserNotFoundException("Пользователь с ID=" + user.getId() + " не найден!");
        }
        jdbcTemplate.update(sqlQuery, user.getId());
        log.info("Удален пользователь с Id={}", user.getId());
        return user;
    }

    @Override
    public User upadateUser(User user) {
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (findById(user.getId()) != null) {
            String sqlQuery = "UPDATE users SET user_name = ?, "
                    + "login = ?, "
                    + "email = ?, "
                    + "birthday = ? "
                    + "WHERE user_id = ?";
            jdbcTemplate.update(sqlQuery,
                    user.getName(),
                    user.getLogin(),
                    user.getEmail(),
                    user.getBirthday(),
                    user.getId());
            log.info("Пользователь с Id={} успешно обновлен", user.getId());
            return user;
        } else {
            throw new UserNotFoundException("Пользователь с Id = " + user.getId() + " не найден!");
        }
    }

    @Override
    public List<User> findAllUsers() {
        String sqlQuery = "SELECT * FROM users";
        log.info("Все пользователи найдены!");
        return jdbcTemplate.query(sqlQuery, userRowMapper());
    }


    @Override
    public User findById(Integer userId) {
        if (userId == null) {
            throw new ValidationException("Передан пустой аргумент!");
        }
        String sqlQuery = "SELECT * FROM users WHERE user_id = ?";
        List<User> users =  jdbcTemplate.query(sqlQuery, userRowMapper(), userId);
        if (users.isEmpty()) {
            throw new UserNotFoundException("Пользователь с Id = " + userId + " не найден!");
        }
        log.info("Пользователь с Id={} найден!", userId);
        return users.get(0);
    }

    private RowMapper<User> userRowMapper() {
        return ((rs, rowNum) -> new User(
                rs.getInt("user_id"),
                rs.getString("email"),
                rs.getString("login"),
                rs.getString("user_name"),
                rs.getDate("birthday").toLocalDate(),
                new HashSet<>()));
    }
}

