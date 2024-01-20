package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GenreService {
    private final GenreDbStorage genreDbStorage;

    public GenreService(GenreDbStorage genreDbStorage) {
        this.genreDbStorage = genreDbStorage;
    }

    public Collection<Genre> findAllGenre() {
        return genreDbStorage.findAllGenre().stream()
                .sorted(Comparator.comparing(Genre::getId))
                .collect(Collectors.toList());
    }

    public Genre getGenreById(Integer genreId) {
        return genreDbStorage.getGenreById(genreId);
    }

    public void addFilmGenres(Film film) {
        genreDbStorage.delete(film);
        genreDbStorage.add(film);
    }

    public Set<Genre> getFilmGenres(Integer filmId) {
        return new HashSet<>(genreDbStorage.getFilmGenres(filmId));
    }
}
