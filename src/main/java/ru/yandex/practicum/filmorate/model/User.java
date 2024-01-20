package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * User.
 */

@Data
@AllArgsConstructor
public class User {
    private int id;
    @NotEmpty
    @Email(message = "Электронная почта не может быть пустой или не содержать @ !")
    private String email;
    @NotEmpty(message = "Поля логин не может быть пустым или содержать пробелы!")
    @Pattern(regexp = "^\\S+$")
    private String login;
    private String name;
    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;
    private Set<Integer> friends = new HashSet<>();

    public void setName(String name) {
        this.name = name == null || name.isBlank() ? login : name;
    }
}
