package storage.mpa;

import lombok.RequiredArgsConstructor;
import model.mpa.Mpa;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DbMpaStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> getAll() {
        String sql = "SELECT * FROM mpa ORDER BY mpa_id";
        try {
            return jdbcTemplate.query(sql, this::mapRowToRate);
        } catch (DataAccessException exception) {
            return null;
        }
    }

    @Override
    public Mpa getById(int id) {
        String sql = "SELECT * FROM mpa WHERE mpa_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRowToRate, id);
        } catch (DataAccessException exception) {
            return null;
        }
    }

    private Mpa mapRowToRate(ResultSet resultSet, int rowNum) throws SQLException {
        return new Mpa(resultSet.getInt("mpa_id"), resultSet.getString("name"));
    }
}