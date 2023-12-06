package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> filmsList = new HashMap<>();
    private int generatedId = 1;

    @GetMapping
    public List<Film> findAllFilms() {
        return new ArrayList<>(filmsList.values());
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) throws ValidationException {
        film.setId(generatedId++);
        filmsList.put(film.getId(), film);
        log.info("Фильм был добавлен в список: " + film.getId() + " " + film.getName() + " " + film.getDescription()
                + " " + film.getDuration() + " " + film.getReleaseDate());

        return film;
    }

    @PutMapping
    public Film addNewOrUpadateFilm(@Valid @RequestBody Film film) throws ValidationException {
        if (filmsList.containsKey(film.getId())) {
            filmsList.put(film.getId(), film);
            log.info("Данные о фильме были изменены: " + film.getId() + " " + film.getName() + " " + film.getDescription()
                    + " " + film.getDuration() + " " + film.getReleaseDate());
        } else {
            log.info("Данного фильма нет в списке: " + film.getName());
            throw new ValidationException("Данного фильма нет в списке: " + film.getName());
        }
        return film;
    }

}
