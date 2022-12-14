package storage.film;

import start.model.film.Film;
import start.model.mpa.Mpa;
import org.junit.jupiter.api.BeforeEach;
import start.storage.film.InMemoryFilmStorage;
import storage.InMemoryStorageTest;

import java.time.LocalDate;
import java.util.Collections;

public class InMemoryFilmStorageTest extends InMemoryStorageTest<Film> {

    @BeforeEach
    void beforeEach() {
        storage = new InMemoryFilmStorage();
    }

    @Override
    protected Film getEntity(int id) {
        return new Film(
                id,
                "Test film",
                "Test description",
                LocalDate.EPOCH,
                120,
                new Mpa(1, "G"),
                Collections.emptyList()
        );
    }

    @Override
    protected Film getEntityForUpdate(int id) {
        return new Film(
                id,
                "Test film updated",
                "Test description updated",
                LocalDate.EPOCH,
                120,
                new Mpa(1, "G"),
                Collections.emptyList()
        );
    }
}
