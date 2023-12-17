package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> filmsList = new HashMap<>();
    private int generatedId = 1;

    @Override
    public Film createFilm(Film film) {
        film.setId(generatedId++);
        filmsList.put(film.getId(), film);
        log.info("Фильм был добавлен в список: " + film.getId() + " " + film.getName() + " " + film.getDescription()
                + " " + film.getDuration() + " " + film.getReleaseDate());
        return film;
    }

    @Override
    public Film removeFilm(Film film) {
        filmsList.remove(film.getId());
        log.info("Фильм был удален из списка: " + film.getId() + " " + film.getName() + " " + film.getDescription()
                + " " + film.getDuration() + " " + film.getReleaseDate());
        return film;
    }

    @Override
    public Film addNewOrUpadateFilm(Film film) throws FilmNotFoundException {
        if (filmsList.containsKey(film.getId())) {
            filmsList.put(film.getId(), film);
            log.info("Данные о фильме были изменены: " + film.getId() + " " + film.getName() + " " + film.getDescription()
                    + " " + film.getDuration() + " " + film.getReleaseDate());
        } else {
            log.info("Данного фильма нет в списке: " + film.getName());
            throw new FilmNotFoundException("Данного фильма нет в списке: " + film.getName());
        }
        return film;
    }

    @Override
    public List<Film> findAllFilms() {
        return new ArrayList<>(filmsList.values());
    }

    @Override
    public Film findById(Integer id) {
        if (filmsList.containsKey(id)) {
            return filmsList.get(id);
        }
        return null;
    }
}
