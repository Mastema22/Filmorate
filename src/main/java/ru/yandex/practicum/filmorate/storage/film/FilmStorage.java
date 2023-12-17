package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;


import java.util.List;

public interface FilmStorage {
    Film createFilm(Film film);

    Film removeFilm(Film film);

    Film addNewOrUpadateFilm(Film film) throws FilmNotFoundException, ValidationException;

    List<Film> findAllFilms();

    Film findById(Integer id);
}
