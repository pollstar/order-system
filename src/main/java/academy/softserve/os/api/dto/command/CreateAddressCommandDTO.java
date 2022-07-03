package academy.softserve.os.api.dto.command;

import lombok.Data;

@Data
public class CreateAddressCommandDTO {
    private String city;
    private String street;
    private String house;
    private String room;
}
