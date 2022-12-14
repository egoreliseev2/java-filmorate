package start.service.genre;


import start.model.genre.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAll();
    Genre getById(int id);
}
