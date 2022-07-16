package academy.softserve.os.api.dto;

import academy.softserve.os.model.Address;
import academy.softserve.os.model.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDTO {

    private Long id;

    private String description;

    private Client client;

    private Address address;
}
