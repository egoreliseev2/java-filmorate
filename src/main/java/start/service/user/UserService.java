package start.service.user;

import start.model.user.User;
import start.model.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User getById(int id);
    User create(UserDto dto);
    User update(UserDto dto);
    User delete(int id);
    void addFriend(int userId, int friendId);
    void deleteFriend(int userId, int friendId);
    List<User> getFriends(int id);
    List<User> getCommonFriends(int userId, int otherUserId);

}
