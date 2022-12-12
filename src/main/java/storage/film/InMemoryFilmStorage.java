package storage.film;
import model.film.Film;
import org.springframework.stereotype.Component;
import storage.InMemoryStorage;

import java.util.List;

@Component("inMemoryFilmStorage")
public class InMemoryFilmStorage extends InMemoryStorage<Film> implements FilmStorage {
    @Override
    protected Film withId(Film film, int id) {
        return film.withId(id);
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        return null;
    }

    @Override
    public void likeFilm(int filmId, int userId) {

    }

    @Override
    public void deleteLikeFromFilm(int filmId, int userId) {

    }
}
