package service.film;

import exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import model.film.Film;
import model.film.dto.FilmDto;
import model.user.User;
import org.springframework.stereotype.Service;
import storage.film.FilmStorage;
import storage.user.UserStorage;
import utils.Mapper;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final Mapper<FilmDto, Film> filmDtoToFilmMapper;

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
        return filmStorage.create(newFilm);
    }

    @Override
    public Film update(FilmDto dto) {
        Film currentFilm = filmStorage.getById(dto.getId());

        if (currentFilm == null) {
            throw new NotFoundException("film");
        }

        Film film = filmDtoToFilmMapper.mapFrom(dto).withLikes(currentFilm.getLikes());

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
    public Film likeFilm(int filmId, int userId) {
        Film film = filmStorage.getById(filmId);
        User user = userStorage.getById(userId);

        if (film == null) {
            throw new NotFoundException("film");
        }

        if (user == null) {
            throw new NotFoundException("user");
        }

        Set<Integer> currentLikes = new LinkedHashSet<>(film.getLikes());
        currentLikes.add(userId);

        Film updatedFilm = film.withLikes(currentLikes);

        return filmStorage.update(filmId, updatedFilm);
    }

    @Override
    public Film deleteLikeFromFilm(int filmId, int userId) {
        Film film = filmStorage.getById(filmId);
        User user = userStorage.getById(userId);

        if (film == null) {
            throw new NotFoundException("film");
        }

        if (user == null) {
            throw new NotFoundException("user");
        }

        Set<Integer> currentLikes = new LinkedHashSet<>(film.getLikes());
        currentLikes.remove(userId);

        Film updatedFilm = film.withLikes(currentLikes);

        return filmStorage.update(filmId, updatedFilm);
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        List<Film> films = filmStorage.getAll();
        return films
                .stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(Math.max(count, 0))
                .collect(Collectors.toList());
    }
}
