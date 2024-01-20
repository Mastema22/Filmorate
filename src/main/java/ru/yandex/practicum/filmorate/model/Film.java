package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.model.anatation.MinimumDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.*;

/**
 * Film.
 */
@Data
@AllArgsConstructor
public class Film {
    private int id;
    @NotEmpty(message = "Название фильма не может быть пустым!")
    private String name;
    @Length(min = 1, max = 200, message = "Максимальная длина описания — 200 символов!")
    private String description;
    @MinimumDate(message = "Дата релиза должна быть не раньше 28 декабря 1895 года!")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private double duration;
    private int rate;
    private Set<Integer> likes;
    @NonNull
    private Mpa mpa;
    private Set<Genre> genres;

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", name);
        values.put("description", description);
        values.put("release_Date", releaseDate);
        values.put("duration", duration);
        values.put("rate", rate);
        values.put("mpa_rate_id", mpa.getId());
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film)) return false;
        Film film = (Film) o;
        return id == film.id && Double.compare(duration, film.duration) == 0 && rate == film.rate && Objects.equals(name, film.name) && Objects.equals(description, film.description) && Objects.equals(releaseDate, film.releaseDate) && Objects.equals(likes, film.likes) && Objects.equals(mpa, film.mpa) && Objects.equals(genres, film.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, releaseDate, duration, likes, rate, mpa, genres);
    }
}