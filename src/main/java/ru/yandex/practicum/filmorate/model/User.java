package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

/**
 * User.
 */
@Data
@AllArgsConstructor
public class User {
    private int id;
    @Email(message = "Электронная почта не может быть пустой или не содержать @ !")
    private String email;
    @NotBlank(message = "Поля логин не может быть пустым или содержать пробелы!")
    private String login;
    private String name;
    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;
}
