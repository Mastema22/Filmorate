package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Component
public class MpaDbStorage {
    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Mpa> findAllMpa() {
        String sqlQuery = "SELECT * FROM mpa_rates";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> new Mpa(
                rs.getInt("rate_id"),
                rs.getString("rate")
        ));
    }

    public Mpa getMpaById(Integer mpaId) {
        Mpa mpa;
        String sqlQuery = "SELECT * FROM mpa_rates WHERE rate_id = ?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sqlQuery, mpaId);
        if (sqlRowSet.first()) {
            mpa = new Mpa(sqlRowSet.getInt("rate_id"),
                    sqlRowSet.getString("rate"));
        } else {
            throw new MpaNotFoundException("Рейтинга с Id =" + mpaId + " нет!");
        }
        return mpa;
    }
}
