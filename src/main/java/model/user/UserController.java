package model.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserMethods userMethods;

    @Autowired
    public UserController(UserMethods userMethods) {
        this.userMethods = userMethods;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userMethods.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return userMethods.getUser(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        log.info("Creating User " + user);
        validateUserLogin(user.getLogin());
        return userMethods.createUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Updating User " + user);
        validateUserLogin(user.getLogin());
        return userMethods.updateUser(user);
    }

    private void validateUserLogin(String login) {
        if (login.contains(" ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
