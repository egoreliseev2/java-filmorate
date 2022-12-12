package service.user;

import exceptions.BadRequestException;
import exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import model.user.User;
import model.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import storage.user.UserStorage;
import utils.Mapper;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final Mapper<UserDto, User> userDtoToUserMapper;

    public UserServiceImpl(
            @Qualifier("dbUserStorage") UserStorage userStorage,
            Mapper<UserDto, User> userDtoToUserMapper
    ) {
        this.userStorage = userStorage;
        this.userDtoToUserMapper = userDtoToUserMapper;
    }

    @Override
    public List<User> getAll() {
        return userStorage.getAll();
    }

    @Override
    public User getById(int id) {
        User user = userStorage.getById(id);

        if (user == null) {
            throw new NotFoundException("user");
        }

        return user;
    }

    @Override
    public User create(UserDto dto) {
        User newUser = userDtoToUserMapper.mapFrom(dto);

        if (emailIsBusy(newUser.getEmail())) {
            throw new BadRequestException("email");
        }

        return userStorage.create(newUser);
    }

    @Override
    public User update(UserDto dto) {
        User currentUser = userStorage.getById(dto.getId());

        if (currentUser == null) {
            throw new NotFoundException("user");
        }

        User user = userDtoToUserMapper.mapFrom(dto);

        if (emailIsBusy(user.getEmail())) {
            throw new BadRequestException("email");
        }

        return userStorage.update(user.getId(), user);
    }

    @Override
    public User delete(int id) {
        User user = userStorage.delete(id);

        if (user == null) {
            throw new NotFoundException("user");
        }

        return user;
    }

    @Override
    public void addFriend(int userId, int friendId) {
        if (userId == friendId) {
            throw new BadRequestException("friendId");
        }

        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);

        if (user == null) {
            throw new NotFoundException("user");
        }

        if (friend == null) {
            throw new NotFoundException("user");
        }

        userStorage.addFriend(userId, friendId);
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);

        if (user == null) {
            throw new NotFoundException("user");
        }

        if (friend == null) {
            throw new NotFoundException("user");
        }

        userStorage.deleteFriend(userId, friendId);
    }

    @Override
    public List<User> getFriends(int id) {
        User user = userStorage.getById(id);

        if (user == null) {
            throw new NotFoundException("user");
        }

        return userStorage.getFriends(id);
    }

    @Override
    public List<User> getCommonFriends(int userId, int otherUserId) {
        User user = userStorage.getById(userId);
        User otherUser = userStorage.getById(otherUserId);

        if (user == null) {
            throw new NotFoundException("user");
        }

        if (otherUser == null) {
            throw new NotFoundException("user");
        }

        return userStorage.getCommonFriends(userId, otherUserId);
    }

    private boolean emailIsBusy(String email) {
        return userStorage.getByEmail(email) != null;
    }
}
