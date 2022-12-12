package model.user.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@With
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    int id;

    @Email(message = "Email is incorrect")
    @NotBlank(message = "Email required")
    String email;

    @NotBlank(message = "Login required")
    @Pattern(regexp = "\\S+", message = "Login must not contain space characters")
    String login;

    String name;

    @NotNull(message = "Birthday required")
    @PastOrPresent(message = "Birthday must not be in the future")
    LocalDate birthday;
}
