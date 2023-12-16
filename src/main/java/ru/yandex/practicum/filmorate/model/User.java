package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * User.
 */
@Slf4j
@Data
@AllArgsConstructor
public class User {
    private int id;
    @NonNull
    @Email(message = "Электронная почта не может быть пустой или не содержать @ !")
    private final String email;
    @NotEmpty(message = "Поля логин не может быть пустым или содержать пробелы!")
    @Pattern(regexp = "^\\S+$")
    private final String login;
    private String name;
    @NonNull
    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private final LocalDate birthday;
    private final Set<Integer> friends = new HashSet<>();
}
