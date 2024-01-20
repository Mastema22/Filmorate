package ru.yandex.practicum.filmorate.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> findAllFilms() {
        return filmService.findAllFilms();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        filmService.createFilm(film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.addNewOrUpadateFilm(film);
    }

    @DeleteMapping
    public Film deleteFilm(@Valid @RequestBody Film film) {
        return filmService.removeFilm(film);
    }

    @GetMapping("/{id}")
    public Film findFilmById(@NonNull @PathVariable("id") Integer id) {
        return filmService.findFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLikeByFilm(@NonNull @PathVariable("id") Integer filmId, @NonNull @PathVariable("userId") Integer userId) {
        return filmService.addLikeByFilm(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLikeByFilm(@NonNull @PathVariable("id") Integer filmId, @NonNull @PathVariable("userId") Integer userId) {
        return filmService.removeLikeByFilm(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> findAllByLikeFilmSort(
            @RequestParam(defaultValue = "10", required = false) Integer count
    ) {
        return filmService.findAllByLikeFilmSort(count);
    }
}