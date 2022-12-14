package exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class ApiException {

    String message;
    String debugMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<String> errors;

    public ApiException(String message, String debugMessage) {
        this.message = message;
        this.debugMessage = debugMessage;
    }
}
