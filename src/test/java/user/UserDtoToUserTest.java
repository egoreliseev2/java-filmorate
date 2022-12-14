package user;

import model.user.User;
import model.user.dto.UserDto;
import model.user.dto.UserDtoToUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

public class UserDtoToUserTest {
    private final UserDtoToUser userDtoToUserMapper = new UserDtoToUser();

    @Test
    void mapFrom_shouldCreateUserWithSameFields() {
        UserDto userDto = new UserDto(
                1,
                "test@test.test",
                "test_login",
                "Test name",
                LocalDate.EPOCH
        );

        User user = userDtoToUserMapper.mapFrom(userDto);

        assertAll(() -> {
            assertThat(user.getId()).isEqualTo(userDto.getId());
            assertThat(user.getEmail()).isEqualTo(userDto.getEmail());
            assertThat(user.getLogin()).isEqualTo(userDto.getLogin());
            assertThat(user.getName()).isEqualTo(userDto.getName());
            assertThat(user.getBirthday()).isEqualTo(userDto.getBirthday());
        });
    }

    @Test
    void mapFrom_shouldCreateUserWithNameEqualsLogin_ifNameIsNotGiven() {
        UserDto userDto = new UserDto(
                1,
                "test@test.test",
                "test_login",
                null,
                LocalDate.EPOCH
        );

        User user = userDtoToUserMapper.mapFrom(userDto);

        assertAll(() -> {
            assertThat(user.getId()).isEqualTo(userDto.getId());
            assertThat(user.getEmail()).isEqualTo(userDto.getEmail());
            assertThat(user.getLogin()).isEqualTo(userDto.getLogin());
            assertThat(user.getName()).isEqualTo(userDto.getLogin());
            assertThat(user.getBirthday()).isEqualTo(userDto.getBirthday());
        });
    }
}
