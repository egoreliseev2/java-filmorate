package start.storage.genre;


import start.model.genre.Genre;

import java.util.List;

public interface GenreStorage {
    List<Genre> getAll();
    List<Genre> getAllByFilmId(int filmId);
    Genre getById(int id);
}
