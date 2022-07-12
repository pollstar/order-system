package academy.softserve.os.api.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateWorkerCommandDTO {

    @NotNull(message = "firstName cannot be null")
    @Size(max = 50, message = "firstName size must not be less than 50 characters")
    private String firstName;

    @NotNull(message = "lastname cannot be null")
    @Size(max = 50, message = "lastName size must not be less than 50 characters")
    private String lastName;

    @NotNull(message = "login cannot be null")
    @Size(max = 20, message = "login size must not be less than 20 characters")
    private String login;

    private String password;
}
