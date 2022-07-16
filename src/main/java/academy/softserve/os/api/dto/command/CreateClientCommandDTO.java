package academy.softserve.os.api.dto.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateClientCommandDTO {
    @NotBlank(message = "Field a name cannot be empty")
    @NotNull(message = "Field a name cannot be null")
    private String name;
}
