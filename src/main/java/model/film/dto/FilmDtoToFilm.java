package model.film.dto;

import model.film.Film;
import org.springframework.stereotype.Component;
import utils.Mapper;

import java.util.LinkedHashSet;

@Component
public class FilmDtoToFilm implements Mapper<FilmDto, Film> {
    @Override
    public Film mapFrom(FilmDto dto) {
        return new Film(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getReleaseDate(),
                dto.getDuration(),
                new LinkedHashSet<>()
        );
    }
}
