package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikeDbStorage likeDbStorage;

    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                       @Qualifier("userDbStorage") UserStorage userStorage,
                       LikeDbStorage likeDbStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likeDbStorage = likeDbStorage;
    }

    public List<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film addNewOrUpadateFilm(Film film) {
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
        if (film != null && userStorage.findById(userId) != null) {
            likeDbStorage.addLike(filmId, userId);
            log.info("Поставлен like к фильму: " + film.getName());
        }
        return film;
    }

    public Film removeLikeByFilm(Integer filmId, Integer userId) {
        Film film = filmStorage.findById(filmId);
        if (film != null && userStorage.findById(userId) != null) {
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
