package academy.softserve.os.api.dto.command;

import academy.softserve.os.model.Address;
import academy.softserve.os.model.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEquipmentCommandDTO {

    @Length(max = 100, message = "Description is too long")
    private String description;

    private Long clientId;

    private Long addressId;
}
