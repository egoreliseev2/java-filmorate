package model.film;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import start.model.film.Film;
import start.model.film.FilmController;
import start.model.film.dto.FilmDto;
import start.model.mpa.Mpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import start.service.film.FilmService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@ExtendWith(MockitoExtension.class)
public class FilmControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private MockMvc mockMvc;

    @Mock
    private FilmService filmService;

    @InjectMocks
    private FilmController filmController;

    @BeforeEach
    void setMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(filmController).build();
    }

    @Test
    void getAllFilms_shouldReturnEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/films"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    void getAllFilms_shouldReturnFilms() throws Exception {
        List<Film> films = List.of(getFilm(1), getFilm(2));

        Mockito.when(filmService.getAll()).thenReturn(films);

        mockMvc.perform(MockMvcRequestBuilders.get("/films"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(films)));
    }

    @Test
    void getFilm_shouldReturnFilm() throws Exception {
        int id = 1;
        Film film = getFilm(id);

        Mockito.when(filmService.getById(id)).thenReturn(film);

        mockMvc.perform(MockMvcRequestBuilders.get("/films/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(film)));
    }

    @Test
    void createFilm_updateFilm_shouldSuccessfullyCreateAndUpdateFilm() throws Exception {
        int id = 1;
        FilmDto filmDto = getFilmDto(id);
        Film film = getFilm(id);
        String json = objectMapper.writeValueAsString(filmDto);

        Mockito.when(filmService.create(filmDto)).thenReturn(film);

        mockMvc.perform(MockMvcRequestBuilders.post("/films").contentType("application/json").content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(film)));

        filmDto = filmDto.withName("Updated name");
        film = film.withName("Updated name");
        json = objectMapper.writeValueAsString(filmDto);

        Mockito.when(filmService.update(filmDto)).thenReturn(film);

        mockMvc.perform(MockMvcRequestBuilders.put("/films").contentType("application/json").content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(film)));
    }


    @ParameterizedTest
    @MethodSource("filmInvalidArgumentsProvider")
    void createFilm_updateFilm_shouldResponseWithBadRequest_ifFilmIsInvalid(FilmDto filmDto) throws Exception {
        String json = objectMapper.writeValueAsString(filmDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/films").contentType("application/json").content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders.put("/films").contentType("application/json").content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleteFilm_shouldSuccessfullyDeleteFilm() throws Exception {
        int id = 1;
        Film film = getFilm(id);

        Mockito.when(filmService.delete(id)).thenReturn(film);

        mockMvc.perform(MockMvcRequestBuilders.delete("/films/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(film)));
    }

    @Test
    void getPopularFilms_shouldReturnPopularFilms() throws Exception {
        int count = 2;
        List<Film> films = List.of(getFilm(1), getFilm(2), getFilm(3));
        List<Film> limitFilms = films.stream().limit(count).collect(Collectors.toList());

        Mockito.when(filmService.getPopularFilms(10)).thenReturn(films);
        Mockito.when(filmService.getPopularFilms(count)).thenReturn(limitFilms);

        mockMvc.perform(MockMvcRequestBuilders.get("/films/popular"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(films)));

        mockMvc.perform(MockMvcRequestBuilders.get("/films/popular?count=" + count))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(limitFilms)));
    }

    private static Film getFilm(int id) {
        return new Film(
                id,
                "Test film",
                "Test description",
                LocalDate.parse("2000-01-01"),
                120,
                new Mpa(1, "G"),
                Collections.emptyList()
        );
    }

    private static FilmDto getFilmDto(int id) {
        return new FilmDto(
                id,
                "Test film",
                "Test description",
                LocalDate.parse("2000-01-01"),
                120,
                new Mpa(1, "G"),
                Collections.emptyList()
        );
    }

    private static Stream<FilmDto> filmInvalidArgumentsProvider() {
        FilmDto filmDto = getFilmDto(1);

        return Stream.of(
                filmDto.withName(null),
                filmDto.withName(""),
                filmDto.withName(" "),
                filmDto.withDescription(null),
                filmDto.withDescription(""),
                filmDto.withDescription(" "),
                filmDto.withDescription("a long string".repeat(20)),
                filmDto.withReleaseDate(null),
                filmDto.withReleaseDate(LocalDate.parse("1000-01-01")),
                filmDto.withDuration(-1)
        );
    }
}
