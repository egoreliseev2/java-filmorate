package model.film.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@With
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FilmDto {
    int id;

    @NotBlank(message = "Name required")
    String name;

    @NotBlank(message = "Description required")
    @Size(max = 200, message = "Description must not be longer than 200 characters")
    String description;

    @NotNull(message = "Release date required")
    LocalDate releaseDate;

    @Positive(message = "Duration required")
    int duration;
}
