package storage.user;

import model.user.User;
import org.springframework.stereotype.Component;
import storage.InMemoryStorage;

import java.util.HashMap;
import java.util.Map;


@Component
public class InMemoryUserStorage extends InMemoryStorage<User> implements UserStorage {
    @Override
    protected User withId(User entity, int id) {
        return entity.withId(id);
    }
}
