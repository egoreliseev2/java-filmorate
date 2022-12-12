package model.user.dto;

import model.user.User;
import org.springframework.stereotype.Component;
import utils.Mapper;


import java.util.LinkedHashSet;

@Component
public class UserDtoToUser implements Mapper<UserDto, User> {
    public User mapFrom(UserDto dto) {
        String name = dto.getName() == null || dto.getName().isBlank() ? dto.getLogin() : dto.getName();

        return new User(
                dto.getId(),
                dto.getEmail(),
                dto.getLogin(),
                name,
                dto.getBirthday()
        );
    }
}
