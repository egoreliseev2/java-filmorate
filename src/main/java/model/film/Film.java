package model.film;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@With
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    int id;

    @NotBlank
    String name;

    @NotBlank
    @Size(max = 200)
    String description;

    @NotNull
    @PastOrPresent
    LocalDate releaseDate;

    @Positive
    int duration;
}
