
import model.film.Film;
import model.user.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationTests {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shoulCreateUser() {
        User user = User.builder()
                .login("login")
                .name("name")
                .email("email@mail.ru")
                .birthday(LocalDate.of(2000,8,20))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldNotCreateUserWithWrongLogin() {
        User user = User.builder()
                .login("")
                .name("name")
                .email("email@mail.ru")
                .birthday(LocalDate.of(2000,8,20))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateUserIfWrongEmail() {
        User user = User.builder()
                .login("login")
                .name("")
                .email("email.ru")
                .birthday(LocalDate.of(2000,8,20))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateUserIfWrongBirthday() {
        User user = User.builder()
                .login("login")
                .name("")
                .email("email@mail.ru")
                .birthday(LocalDate.of(2222,8,20))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldCreateFilm() {
        Film film = Film.builder()
                .name("film")
                .description("fdahfafa")
                .releaseDate(LocalDate.of(1999,1,2))
                .duration(120)
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldNotCreateFilmIfWrongName() {
        Film film = Film.builder()
                .name("")
                .description("fdafafa")
                .releaseDate(LocalDate.of(1999,3,25))
                .duration(200)
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateFilmIfWrongDescription() {
        Film film = Film.builder()
                .name("name")
                .description("")
                .releaseDate(LocalDate.of(1999,3,25))
                .duration(200)
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateFilmIfWrongReleaseDate() {
        Film film = Film.builder()
                .name("name")
                .description("asfasfsa")
                .releaseDate(LocalDate.of(1890,3,25))
                .duration(200)
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateFilmIfWrongFilmDuration() {
        Film film = Film.builder()
                .name("name")
                .description("fhsakjfasf")
                .releaseDate(LocalDate.of(1980,3,25))
                .duration(-200)
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

}
