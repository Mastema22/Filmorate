package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;

    public List<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film addNewOrUpadateFilm(Film film) throws ValidationException {
        return filmStorage.addNewOrUpadateFilm(film);
    }

    public Film findFilmById(Integer id) {
        Film film = filmStorage.findById(id);
        if (film == null) {
            throw new FilmNotFoundException("Данного фильма нет в списке!");
        }
        return film;
    }

    public Film removeFilm(Film film) {
        return filmStorage.removeFilm(film);
    }

    public Film addLikeByFilm(Integer filmId, Integer userId) {
        Film film = filmStorage.findById(filmId);
        if (film != null && userService.findUserById(userId) != null) {
            filmStorage.findById(filmId).getLikes().add(userId);
            log.info("Поставлен like к фильму: " + film.getName());
        }
        return film;
    }

    public Film removeLikeByFilm(Integer filmId, Integer userId) {
        Film film = filmStorage.findById(filmId);
        if (film != null && userService.findUserById(userId) != null) {
            film.getLikes().remove(userId);
            log.info("Удален like к фильму: " + film.getName());
        }
        return film;
    }

    public List<Film> findAllByLikeFilmSort(Integer count) {
        return filmStorage.findAllFilms().stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

}
