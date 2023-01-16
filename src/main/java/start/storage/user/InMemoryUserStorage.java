package start.storage.user;

import start.model.user.User;
import org.springframework.stereotype.Component;
import start.storage.InMemoryStorage;

import java.util.List;

@Component
public class InMemoryUserStorage extends InMemoryStorage<User> implements UserStorage {
    @Override
    protected User withId(User user, int id) {
        return user.withId(id);
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public void addFriend(int userId, int friendId) {

    }

    @Override
    public void deleteFriend(int userId, int friendId) {

    }

    @Override
    public List<User> getFriends(int id) {
        return null;
    }

    @Override
    public List<User> getCommonFriends(int userId, int otherId) {
        return null;
    }


}
