package storage.user;

import model.user.User;

import java.util.List;

public interface UserStorage {
    List<User> getAll();
    User getById(int id);
    User getByEmail(String email);
    User create(User film);
    User update(int id, User film);
    User delete(int id);
    void addFriend(int userId, int friendId);
    void deleteFriend(int userId, int friendId);
    List<User> getFriends(int id);
    List<User> getCommonFriends(int userId, int otherUserId);
}
