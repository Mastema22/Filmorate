package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> filmsList = new HashMap<>();
    private int generatedId = 0;

    @GetMapping
    public List<Film> findAllFilms() {
        return new ArrayList<>(filmsList.values());
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) throws ValidationException {
        if (film.getName().isEmpty()) {
            log.debug("Название фильма не может быть пустым: " + film);
            throw new ValidationException("Название фильма не может быть пустым!");
        }
        if (film.getDescription().length() > 200) {
            log.debug("Максимальная длина описания — 200 символов :" + film.getDescription());
            throw new ValidationException("Максимальная длина описания — 200 символов!");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.debug("Дата релиза должна быть не раньше 28 декабря 1895 года: " + film.getReleaseDate());
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года!");
        }
        if (film.getDuration() < 0) {
            log.debug("Продолжительность фильма должна быть положительной: " + film.getDuration());
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
        film.setId(++generatedId);
        filmsList.put(film.getId(), film);
        log.info("Фильм был добавлен в список: " + film.getId() + " " + film.getName() + " " + film.getDescription()
                + " " + film.getDuration() + " " + film.getReleaseDate());

        return film;
    }

    @PutMapping
    public Film addNewOrUpadateFilm(@RequestBody Film film) throws ValidationException {
        if (film.getName().isEmpty()) {
            log.debug("Название фильма не может быть пустым: " + film);
            throw new ValidationException("Название фильма не может быть пустым!");
        }
        if (film.getDescription().length() > 200) {
            log.debug("Максимальная длина описания — 200 символов :" + film.getDescription());
            throw new ValidationException("Максимальная длина описания — 200 символов!");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.debug("Дата релиза должна быть не раньше 28 декабря 1895 года: " + film.getReleaseDate());
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года!");
        }
        if (film.getDuration() < 0) {
            log.debug("Продолжительность фильма должна быть положительной: " + film.getDuration());
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
        filmsList.forEach((key, value) -> {
            if (film.getId() == key) {
                filmsList.replace(key, film);
                log.info("Данные о фильме были изменены: " + film.getId() + " " + film.getName() + " " + film.getDescription()
                        + " " + film.getDuration() + " " + film.getReleaseDate());
            } else {
                try {
                    log.info("Данного фильма нет в списке: " + film.getName());
                    throw new ValidationException("Данного фильма нет в списке: " + film.getName());
                } catch (ValidationException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return film;
    }
}
