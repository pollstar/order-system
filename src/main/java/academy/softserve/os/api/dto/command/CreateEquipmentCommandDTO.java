package academy.softserve.os.api.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

<<<<<<< HEAD
import javax.validation.constraints.NotNull;

=======
>>>>>>> c8c801e (ORD-20: Equipment API: Query Equipment)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEquipmentCommandDTO {

    @Length(max = 255, message = "Description is too long")
    private String description;

    @NotNull(message = "Client ID not be null")
    private Long clientId;

    @NotNull(message = "Address ID not be null")
    private Long addressId;
}
