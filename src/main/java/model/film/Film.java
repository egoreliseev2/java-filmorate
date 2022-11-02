package model.film;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;
import java.time.LocalDate;

@With
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(makeFinal=false, level=AccessLevel.PRIVATE)
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
