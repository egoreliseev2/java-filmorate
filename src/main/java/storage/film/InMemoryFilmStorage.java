package storage.film;
import model.film.Film;
import org.springframework.stereotype.Component;
import storage.InMemoryStorage;

@Component
public class InMemoryFilmStorage extends InMemoryStorage<Film> implements FilmStorage {
    @Override
    protected Film withId(Film entity, int id) {
        return entity.withId(id);
    }
}
