package start.storage.film;

import lombok.RequiredArgsConstructor;
import start.model.film.Film;
import start.model.genre.Genre;
import start.model.mpa.Mpa;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import start.storage.genre.GenreStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository("dbFilmStorage")
@RequiredArgsConstructor
public class DbFilmStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreStorage genreStorage;

    @Override
    public List<Film> getAll() {
        return jdbcTemplate.query(FilmQueries.GET_ALL, this::mapRowToFilm);
    }

    @Override
    public Film getById(int id) {
        try {
            return jdbcTemplate.queryForObject(FilmQueries.GET_BY_ID, this::mapRowToFilm, id);
        } catch (DataAccessException exception) {
            return null;
        }
    }

    @Override
    public Film create(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("FILM")
                .usingGeneratedKeyColumns("FILM_ID");

        Map<String, Object> filmColumns = new HashMap<>();
        filmColumns.put("NAME", film.getName());
        filmColumns.put("DESCRIPTION", film.getDescription());
        filmColumns.put("RELEASE_DATE", film.getReleaseDate());
        filmColumns.put("DURATION", film.getDuration());
        filmColumns.put("MPA_ID", film.getMpa().getId());

        int filmId = simpleJdbcInsert.executeAndReturnKey(filmColumns).intValue();

        updateFilmGenres(film.getGenres(), filmId);

        return getById(filmId);
    }

    @Override
    public Film update(int id, Film film) {
        jdbcTemplate.update(
                FilmQueries.UPDATE,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                id
        );

        jdbcTemplate.update(FilmQueries.DELETE_FILM_GENRES, film.getId());
        updateFilmGenres(film.getGenres(), id);

        return getById(id);
    }

    @Override
    public Film delete(int id) {
        Film film = getById(id);

        if (film == null) {
            return null;
        }

        jdbcTemplate.update(FilmQueries.DELETE, id);

        return film;
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        return jdbcTemplate.query(FilmQueries.GET_POPULAR_FILMS, this::mapRowToFilm, Math.max(count, 0));
    }

    @Override
    public void likeFilm(int filmId, int userId) {
        jdbcTemplate.update(FilmQueries.LIKE_FILM, filmId, userId);
    }

    @Override
    public void deleteLikeFromFilm(int filmId, int userId) {
        jdbcTemplate.update(FilmQueries.DELETE_LIKE_FROM_FILM, filmId, userId);
    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return new Film(
                resultSet.getInt("FILM_ID"),
                resultSet.getString("FILM.NAME"),
                resultSet.getString("DESCRIPTION"),
                resultSet.getDate("RELEASE_DATE").toLocalDate(),
                resultSet.getInt("DURATION"),
                new Mpa(resultSet.getInt("MPA.MPA_ID"), resultSet.getString("MPA.NAME")),
                genreStorage.getAllByFilmId(resultSet.getInt("FILM_ID"))
        );
    }

    private void updateFilmGenres(List<Genre> genres, int filmId) {
        if (genres == null) {
            return;
        }

        List<Integer> genreUniqueIds = genres.stream()
                .map(Genre::getId)
                .distinct()
                .collect(Collectors.toList());

        jdbcTemplate.batchUpdate(
                FilmQueries.ADD_GENRE,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        int genreId = genreUniqueIds.get(i);
                        ps.setInt(1, filmId);
                        ps.setInt(2, genreId);
                    }

                    public int getBatchSize() {
                        return genreUniqueIds.size();
                    }
                });
    }
}
