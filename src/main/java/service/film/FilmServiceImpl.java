package service.film;

import exceptions.NotFoundException;
import model.film.Film;
import model.film.dto.FilmDto;
import model.mpa.Mpa;
import model.user.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import storage.film.FilmStorage;
import storage.genre.GenreStorage;
import storage.mpa.MpaStorage;
import storage.user.UserStorage;
import utils.Mapper;

import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;
    private final Mapper<FilmDto, Film> filmDtoToFilmMapper;

    public FilmServiceImpl(
            @Qualifier("dbFilmStorage") FilmStorage filmStorage,
            @Qualifier("dbUserStorage") UserStorage userStorage,
            MpaStorage mpaStorage,
            GenreStorage genreStorage,
            Mapper<FilmDto, Film> filmDtoToFilmMapper
    ) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.mpaStorage = mpaStorage;
        this.genreStorage = genreStorage;
        this.filmDtoToFilmMapper = filmDtoToFilmMapper;
    }
    @Override
    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    @Override
    public Film getById(int id) {
        Film film = filmStorage.getById(id);

        if (film == null) {
            throw new NotFoundException("film");
        }

        return film;
    }

    @Override
    public Film create(FilmDto dto) {
        Film newFilm = filmDtoToFilmMapper.mapFrom(dto);
        Mpa mpa = mpaStorage.getById(newFilm.getMpa().getId());
        if(mpa == null){
            throw new NotFoundException("mpa");
        }

        newFilm.getGenres().forEach(genre -> {
            if (genreStorage.getById(genre.getId()) == null) {
                throw new NotFoundException("genre");
            }
        });
        return filmStorage.create(newFilm);
    }

    @Override
    public Film update(FilmDto dto) {
        Film currentFilm = filmStorage.getById(dto.getId());

        if (currentFilm == null) {
            throw new NotFoundException("film");
        }

        Film film = filmDtoToFilmMapper.mapFrom(dto);

        if (mpaStorage.getById(film.getMpa().getId()) == null) {
            throw new NotFoundException("mpa");
        }

        film.getGenres().forEach(genre -> {
            if (genreStorage.getById(genre.getId()) == null) {
                throw new NotFoundException("genre");
            }
        });

        return filmStorage.update(film.getId(), film);
    }

    @Override
    public Film delete(int id) {
        Film film = filmStorage.delete(id);

        if (film == null) {
            throw new NotFoundException("film");
        }

        return film;
    }

    @Override
    public void likeFilm(int filmId, int userId) {
        Film film = filmStorage.getById(filmId);
        User user = userStorage.getById(userId);

        if (film == null) {
            throw new NotFoundException("film");
        }

        if (user == null) {
            throw new NotFoundException("user");
        }
        filmStorage.likeFilm(filmId,userId);
    }

    @Override
    public void deleteLikeFromFilm(int filmId, int userId) {
        Film film = filmStorage.getById(filmId);
        User user = userStorage.getById(userId);
        if (film == null) {
            throw new NotFoundException("film");
        }

        if (user == null) {
            throw new NotFoundException("user");
        }

        filmStorage.deleteLikeFromFilm(filmId, userId);
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        return filmStorage.getPopularFilms(count);
    }
}
