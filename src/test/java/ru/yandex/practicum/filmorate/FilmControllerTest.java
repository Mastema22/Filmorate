package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmControllerTest {
    private final FilmService filmService;
    private final UserService userService;
    private static Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Test
    public void shouldCreateFilm()  {
        Film film = new Film(1, "Иван грозный", "Описание", LocalDate.of(2022, 12, 6), 192);
        filmService.createFilm(film);
        assertTrue(filmService.findAllFilms().contains(film));
    }

    @Test
    public void shouldNotPassNameValidation() {
        Film film = new Film(1, "", "Описание", LocalDate.of(2022, 12, 6), 192);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
    }

    @Test
    public void shouldNotPassDescriptionValidation() {
        Film film = new Film(1, "Аватар", "«Аватар: Путь воды» (англ. Avatar: The Way of Water) — американский " +
                "научно-фантастический фильм режиссёра и сценариста Джеймса Кэмерона. Является сиквелом" +
                " фильма «Аватар» 2009 года. Изначально премьера фильма была запланирована на 17 декабря 2021" +
                " года, но в связи с пандемией коронавируса 2020 года была перенесена на 16 декабря 2022 года.", LocalDate.of(2022, 12, 6), 192);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
    }

    @Test
    public void shouldNotPassReleaseDateValidationInThePast() {
        Film film = new Film(1, "Иван грозный", "Описание", LocalDate.of(1699, 12, 6), 192);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
    }

    @Test
    public void shouldPassReleaseDateValidationInTheFuture() {
        Film film = new Film(1, "Иван грозный", "Описание", LocalDate.of(2025, 12, 6), 192);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(0, violations.size());
    }

    @Test
    public void shouldNotPassDurationValidation() {
        Film film = new Film(1, "Иван грозный", "Описание", LocalDate.of(2022, 12, 6), -192);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
    }

    @Test
    public void shouldUpdateFilm() throws FilmNotFoundException, ValidationException {
        Film film = new Film(1, "Иван грозный", "Описание", LocalDate.of(2022, 12, 6), 192);
        filmService.createFilm(film);
        Film filmUpdate = new Film(film.getId(), "Иван грозный", "Описание дополнено", LocalDate.of(1993, 12, 6), 192);
        filmService.addNewOrUpadateFilm(filmUpdate);
        assertEquals(filmUpdate, filmService.findFilmById(film.getId()));
    }

    @Test
    public void shouldPassDescriptionValidationWith200Symbols() {
        Film film = new Film(1, "Иван грозный", "«Аватар: Путь воды» (англ. Avatar: The Way of Water) — американский " +
                "научно-фантастический фильм режиссёра и сценариста Джеймса Кэмерона. Является сиквелом " +
                "фильма «Аватар» 2009 года.Изначально премьера", LocalDate.of(2022, 12, 6), 192);
        filmService.createFilm(film);
        assertTrue(filmService.findAllFilms().contains(film));
    }

    @Test
    public void shouldPassReleaseDateValidation() {
        Film film = new Film(1, "Иван грозный", "Описание", LocalDate.of(1895, 12, 28), 192);
        filmService.createFilm(film);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(0, violations.size());
    }

    @Test
    public void shouldFindFilmById() {
        Film film = new Film(1, "Иван грозный", "Описание", LocalDate.of(1895, 12, 28), 192);
        filmService.createFilm(film);

        assertEquals(film, filmService.findFilmById(film.getId()));
    }

    @Test
    public void shouldAddLike() throws ValidationException {
        User user = new User(15, "qwert@mail.ru", "Mango", "Игнат", LocalDate.of(2002, 8, 15));
        userService.createUser(user);
        Film film = new Film(1, "Иван грозный", "Описание", LocalDate.of(1995, 12, 28), 192);
        filmService.createFilm(film);
        filmService.addLikeByFilm(film.getId(),user.getId());
        assertEquals(1, film.getLikes().size());
    }

    @Test
    public void shouldDeleteLike() throws ValidationException {
        User user = new User(1, "qwert@mail.ru", "Mango", "Игнат", LocalDate.of(2000, 8, 15));
        userService.createUser(user);
        Film film = new Film(1, "Иван грозный", "Описание", LocalDate.of(1895, 12, 28), 192);
        filmService.createFilm(film);
        filmService.addLikeByFilm(film.getId(), user.getId());
        filmService.removeLikeByFilm(film.getId(), user.getId());
        assertEquals(0, film.getLikes().size());
    }

    @Test
    public void shouldGetMostPopularFilms() throws ValidationException {
        User user = new User(1, "qwert@mail.ru", "Mango", "Игнат", LocalDate.of(2000, 8, 15));
        userService.createUser(user);
        User secondUser = new User(2, "bem@mail.ru", "Riki", "Bem", LocalDate.of(2005, 1, 03));
        userService.createUser(secondUser);
        Film film = new Film(1, "Иван грозный", "Описание", LocalDate.of(1895, 12, 28), 192);
        filmService.createFilm(film);
        filmService.addLikeByFilm(film.getId(), user.getId());
        filmService.addLikeByFilm(film.getId(), secondUser.getId());
        assertEquals(List.of(filmService.findFilmById(film.getId())), filmService.findAllByLikeFilmSort(1));
    }

}
