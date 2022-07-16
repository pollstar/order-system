package academy.softserve.os.api.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserCommandDTO {

    @Size(max = 20, message = "login size must not be more than 20 characters")
    @NotNull(message = "login cannot be null")
    private String login;

    @NotNull(message = "user roles must not be null")
    private Set<String> roles;
    @NotNull(message = "password cannot be null")
    private String password;
}
