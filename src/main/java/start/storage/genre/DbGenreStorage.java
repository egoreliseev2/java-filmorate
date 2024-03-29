package start.storage.genre;
import lombok.RequiredArgsConstructor;
import start.model.genre.Genre;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DbGenreStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getAll() {
        String sql = "SELECT * FROM GENRE ORDER BY genre_id";
        return jdbcTemplate.query(sql, this::mapRowToGenre);
    }

    @Override
    public Genre getById(int id) {
        String sql = "SELECT * FROM GENRE WHERE genre_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRowToGenre, id);
        } catch (DataAccessException exception) {
            return null;
        }
    }

    @Override
    public List<Genre> getAllByFilmId(int filmId) {
        String sql = "SELECT fg.genre_id AS genre_id, g.name AS name " +
                "FROM FILM_GENRE AS fg " +
                "JOIN GENRE AS g ON fg.genre_id = g.genre_id " +
                "WHERE fg.film_id = ? ";
        try {
            return jdbcTemplate.query(sql, this::mapRowToGenre, filmId);
        } catch (DataAccessException exception) {
            return null;
        }
    }

    private Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return new Genre(resultSet.getInt("genre_id"), resultSet.getString("name"));
    }
}
