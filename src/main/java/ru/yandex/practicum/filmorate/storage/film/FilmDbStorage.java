package ru.yandex.practicum.filmorate.storage.film;


import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.service.MpaService;
import ru.yandex.practicum.filmorate.storage.like.LikeDbStorage;


import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final MpaService mpaService;
    private final GenreService genreService;
    private final LikeDbStorage likeDbStorage;

    public FilmDbStorage(JdbcTemplate jdbcTemplate, MpaService mpaService, GenreService genreService, LikeDbStorage likeDbStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaService = mpaService;
        this.genreService = genreService;
        this.likeDbStorage = likeDbStorage;
    }

    @Override
    public Film createFilm(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");
        film.setId(simpleJdbcInsert.executeAndReturnKey(film.toMap()).intValue());
        film.setMpa(mpaService.getMpaById(film.getMpa().getId()));
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                genre.setName(genreService.getGenreById(genre.getId()).getName());
            }
            genreService.addFilmGenres(film);
        }
        log.info("Добавлен фильм с Id={} ", film.getId());
        return film;
    }

    @Override
    public Film removeFilm(Film film) {
        String sqlQuery = "DELETE FROM films WHERE users_id = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
        log.info("Фильм удален с Id={} ", film.getId());
        return film;
    }

    @Override
    public Film addNewOrUpadateFilm(Film film) {
        String sqlQuery = "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, mpa_rate_id = ? WHERE film_id = ?";
        if (jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId()) != 0) {
            film.setMpa(mpaService.getMpaById(film.getMpa().getId()));
            if (film.getGenres() != null) {
                Collection<Genre> sortGenres = film.getGenres().stream()
                        .sorted(Comparator.comparing(Genre::getId))
                        .collect(Collectors.toList());
                film.setGenres(new LinkedHashSet<>(sortGenres));
                for (Genre genre : film.getGenres()) {
                    genre.setName(genreService.getGenreById(genre.getId()).getName());
                }
            }
            genreService.addFilmGenres(film);
            log.info("Фильм обновлен с Id={}", film.getId());
            return film;
        } else {
            throw new FilmNotFoundException("Фильм с Id = " + film.getId() + " не найден");
        }
    }

    @Override
    public List<Film> findAllFilms() {
        String sqlQuery = "SELECT * FROM films";
        log.info("Поиск всех фильмов");
        return jdbcTemplate.query(sqlQuery, filmRowMapper());
    }

    @Override
    public Film findById(Integer filmId) {
        String sqlQuery = "SELECT * FROM films WHERE film_id = ?";
        Film film;
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sqlQuery, filmId);
        if (filmRows.first()) {
            film = new Film(
                    filmRows.getInt("film_id"),
                    Objects.requireNonNull(filmRows.getString("name")),
                    Objects.requireNonNull(filmRows.getString("description")),
                    Objects.requireNonNull(filmRows.getDate("release_date")).toLocalDate(),
                    filmRows.getInt("duration"),
                    filmRows.getInt("rate"),
                    new HashSet<>(likeDbStorage.getLikes(filmRows.getInt("film_id"))),
                    mpaService.getMpaById(filmRows.getInt("mpa_rate_id")),
                    new HashSet<>(genreService.getFilmGenres(filmId)));
        } else {
            throw new FilmNotFoundException("Фильм с Id= " + filmId + " не найден!");
        }
        log.info("Фильм с Id={} найден", film.getId());
        return film;
    }

    private RowMapper<Film> filmRowMapper() {
        return ((rs, rowNum) ->
                new Film(
                        rs.getInt("film_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDate("release_date").toLocalDate(),
                        rs.getInt("duration"),
                        rs.getInt("rate"),
                        new HashSet<>(likeDbStorage.getLikes(rs.getInt("film_id"))),
                        mpaService.getMpaById(rs.getInt("mpa_rate_id")),
                        new HashSet<>(genreService.getFilmGenres(rs.getInt("film_id"))))

        );
    }

}

