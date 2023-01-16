package model.film.dto;

import start.model.film.Film;
import start.model.film.dto.FilmDto;
import start.model.film.dto.FilmDtoToFilm;
import start.model.mpa.Mpa;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Collections;

public class FilmDtoToFilmTest {
    private final FilmDtoToFilm filmDtoToFilmMapper = new FilmDtoToFilm();

    @Test
    void mapFrom_shouldCreateFilmWithSameFields() {
        FilmDto filmDto = new FilmDto(
                1,
                "Test film",
                "Test description",
                LocalDate.EPOCH,
                120,
                new Mpa(1, "G"),
                Collections.emptyList()
        );

        Film film = filmDtoToFilmMapper.mapFrom(filmDto);

        assertAll(() -> {
            assertThat(film.getId()).isEqualTo(filmDto.getId());
            assertThat(film.getName()).isEqualTo(filmDto.getName());
            assertThat(film.getDescription()).isEqualTo(filmDto.getDescription());
            assertThat(film.getReleaseDate()).isEqualTo(filmDto.getReleaseDate());
            assertThat(film.getDuration()).isEqualTo(filmDto.getDuration());
            assertThat(film.getMpa()).isEqualTo(filmDto.getMpa());
            assertThat(film.getGenres()).isEqualTo(Collections.emptyList());
        });
    }
}
