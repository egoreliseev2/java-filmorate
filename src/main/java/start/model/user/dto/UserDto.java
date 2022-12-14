package start.model.user.dto;

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

    @Email(message = "Почта не соответствует формату")
    @NotBlank(message = "Почта не может быть пустым")
    String email;

    @NotBlank(message = "Логин не может быть пустым")
    @Pattern(regexp = "\\S+", message = "Логин не может содержать пробел")
    String login;

    String name;

    @NotNull(message = "День рождение не может быть пустым")
    @PastOrPresent(message = "День рождение не может быть в будущем")
    LocalDate birthday;
}
