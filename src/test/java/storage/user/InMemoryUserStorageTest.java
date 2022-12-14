package storage.user;

import model.user.User;
import org.junit.jupiter.api.BeforeEach;
import storage.InMemoryStorageTest;
import storage.user.InMemoryUserStorage;

import java.time.LocalDate;

public class InMemoryUserStorageTest extends InMemoryStorageTest<User> {
    @BeforeEach
    void beforeEach() {
        storage = new InMemoryUserStorage();
    }

    @Override
    protected User getEntity(int id) {
        return new User(
                id,
                "test@test.test",
                "Test login",
                "Test name",
                LocalDate.EPOCH
        );
    }

    @Override
    protected User getEntityForUpdate(int id) {
        return new User(
                id,
                "test@test.test",
                "Test login updated",
                "Test name updated",
                LocalDate.EPOCH
        );
    }
}
