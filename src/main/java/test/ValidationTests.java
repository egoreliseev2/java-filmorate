package test;

import model.film.Film;
import model.user.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
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
        User user = getUser();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldNotCreateUserWithWrongLogin() {
        User user = getUser().withLogin("");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateUserIfWrongEmail() {
        User user = getUser().withEmail("email");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateUserIfWrongBirthday() {
        User user = getUser().withBirthday(LocalDate.of(2222,12,2));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldCreateFilm() {
        Film film = getFilm();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldNotCreateFilmIfWrongName() {
        Film film = getFilm().withName("");

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateFilmIfWrongDescription() {
        Film film = getFilm().withDescription("");

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateFilmIfWrongReleaseDate() {
        Film film = getFilm().withReleaseDate(LocalDate.of(1890,3,25));

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateFilmIfWrongFilmDuration() {
        Film film = getFilm().withDuration(-200);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    private Film getFilm() {
        return new Film(1, "Interesting", "Interesting film", LocalDate.of(2000,1,2), 120, Collections.emptySet());
    }

    public User getUser(){
        return new User(1,"email@mail.ru","login","myName",LocalDate.of(2000,3,20), Collections.emptySet());
    }

}
