package academy.softserve.os.service.command;

import academy.softserve.os.model.Address;
import academy.softserve.os.model.Client;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateEquipmentCommand {

    private String description;

    private Long clientId;

    private Long addressId;
}
