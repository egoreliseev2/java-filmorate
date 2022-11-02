package model.film;

import java.util.List;
public interface FilmMethods {

    List<Film> getAllFilms();
    Film getFilm(int id);
    Film createFilm(Film film);
    Film updateFilm(Film film);
}
