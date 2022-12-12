package service.genre;

import exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import model.genre.Genre;
import org.springframework.stereotype.Service;
import storage.genre.GenreStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreStorage genreStorage;

    @Override
    public List<Genre> getAll() {
        return genreStorage.getAll();
    }

    @Override
    public Genre getById(int id) {
        Genre genre = genreStorage.getById(id);

        if (genre == null) {
            throw new NotFoundException("genre");
        }

        return genre;
    }
}
