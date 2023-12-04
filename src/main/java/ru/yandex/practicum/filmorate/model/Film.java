package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Film.
 */
@Data
@AllArgsConstructor
public class Film {
    private int id;
    @NotBlank(message = "Название фильма не может быть пустым!")
    private String name;
    @NotBlank(message = "Описание не может быть пустым!")
    @Size(max = 200, message = "Максимальная длина описания — 200 символов!")
    private String description;
    private LocalDate releaseDate;
    @PositiveOrZero(message = "Продолжительность фильма должна быть положительной")
    private double duration;
}
