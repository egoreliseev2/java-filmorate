package start.service.film;

import start.model.film.Film;
import start.model.film.dto.FilmDto;


import java.util.List;
public interface FilmService {
    List<Film> getAll();
    Film getById(int id);
    Film create(FilmDto dto);
    Film update(FilmDto dto);
    Film delete(int id);
    void likeFilm(int filmId, int userId);
    void deleteLikeFromFilm(int filmId, int userId);
    List<Film> getPopularFilms(int count);
}
