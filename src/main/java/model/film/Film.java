package model.film;

import lombok.Value;
import lombok.With;
import model.genre.Genre;
import model.mpa.Mpa;
import java.time.LocalDate;
import java.util.List;

@With
@Value
public class Film {
    int id;
    String name;
    String description;
    LocalDate releaseDate;
    int duration;
    Mpa mpa;
    List<Genre> genres;
}
