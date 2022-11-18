package model.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@With
@Value
public class User {
    int id;
    String email;
    String login;
    String name;
    LocalDate birthday;
    Set<Integer> friends;
}


