package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.model.anatation.MinimumDate;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film.
 */
@Data
@AllArgsConstructor
public class Film {
    private int id;
    @NotEmpty(message = "Название фильма не может быть пустым!")
    private final String name;
    @Length(max = 200, message = "Максимальная длина описания — 200 символов!")
    private final String description;
    @MinimumDate(message = "Дата релиза должна быть не раньше 28 декабря 1895 года!")
    private final LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной")
    @NonNull
    private final double duration;
    private final Set<Integer> likes = new HashSet<>();
}
