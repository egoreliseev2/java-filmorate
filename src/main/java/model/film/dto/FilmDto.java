package model.film.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import model.genre.Genre;
import model.mpa.Mpa;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@With
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FilmDto {
    int id;

    @NotBlank(message = "Имя не может быть пустым")
    String name;

    @NotBlank(message = "Описание не может быть пустым")
    @Size(max = 200, message = "Описание не может быть более чем 200 символов")
    String description;

    @NotNull(message = "Дата выхода не может быть пустым")
    LocalDate releaseDate;

    @Positive(message = "Длительность не может быть пустым")
    int duration;

    @NotNull(message = "Mpa required")
    Mpa mpa;

    List<Genre> genres;
}
