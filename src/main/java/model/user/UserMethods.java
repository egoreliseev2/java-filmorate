package model.user;

import java.util.List;

public interface UserMethods {
    List<User> getAllUsers();
    User getUser(int id);
    User createUser(User user);
    User updateUser(User user);
}
