package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;


import java.util.List;

@Component
public interface FilmStorage {
    Film createFilm(Film film);

    Film removeFilm(Film film);

    Film addNewOrUpadateFilm(Film film);

    List<Film> findAllFilms();

    Film findById(Integer id);
}
