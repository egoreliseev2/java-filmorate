package model.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@With
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(makeFinal=false, level=AccessLevel.PRIVATE)
public class User {
    int id;

    @Email
    String email;

    @NotBlank
    String login;

    String name;

    @PastOrPresent
    LocalDate birthday;
}
