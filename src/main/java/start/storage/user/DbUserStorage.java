package start.storage.user;

import lombok.RequiredArgsConstructor;
import start.model.user.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("dbUserStorage")
@RequiredArgsConstructor
public class DbUserStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(UserQueries.GET_ALL, this::mapRowToUser);
    }

    @Override
    public User getById(int id) {
        try {
            return jdbcTemplate.queryForObject(UserQueries.GET_BY_ID, this::mapRowToUser, id);
        } catch (DataAccessException exception) {
            return null;
        }
    }

    @Override
    public User getByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(UserQueries.GET_BY_EMAIL, this::mapRowToUser, email);
        } catch (DataAccessException exception) {
            return null;
        }
    }

    @Override
    public User create(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("USER_ID");

        Map<String, Object> userColumns = new HashMap<>();
        userColumns.put("EMAIL", user.getEmail());
        userColumns.put("LOGIN", user.getLogin());
        userColumns.put("NAME", user.getName());
        userColumns.put("BIRTHDAY", user.getBirthday());

        int userId = simpleJdbcInsert.executeAndReturnKey(userColumns).intValue();

        return getById(userId);
    }

    @Override
    public User update(int id, User user) {
        jdbcTemplate.update(
                UserQueries.update,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                id
        );

        return getById(id);
    }

    @Override
    public User delete(int id) {
        User user = getById(id);

        if (user == null) {
            return null;
        }

        jdbcTemplate.update(UserQueries.DELETE, id);

        return user;
    }

    @Override
    public List<User> getFriends(int id) {
        return jdbcTemplate.query(UserQueries.GET_FRIENDS, this::mapRowToUser, id);
    }

    @Override
    public List<User> getCommonFriends(int userId, int otherUserId) {
        return jdbcTemplate.query(UserQueries.GET_COMMON_FRIENDS, this::mapRowToUser, userId, otherUserId);
    }

    @Override
    public void addFriend(int userId, int friendId) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("FRIENDSHIP");

        Map<String, Object> userColumns = new HashMap<>();
        userColumns.put("USER_ID", userId);
        userColumns.put("FRIEND_ID", friendId);

        simpleJdbcInsert.execute(userColumns);
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        jdbcTemplate.update(UserQueries.DELETE_FRIEND, userId, friendId);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
                resultSet.getInt("USER_ID"),
                resultSet.getString("EMAIL"),
                resultSet.getString("LOGIN"),
                resultSet.getString("NAME"),
                resultSet.getDate("BIRTHDAY").toLocalDate()
        );
    }
}
