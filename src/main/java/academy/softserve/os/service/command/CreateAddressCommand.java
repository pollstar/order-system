package academy.softserve.os.service.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAddressCommand {
    private String city;
    private String street;
    private String house;
    private String room;
}
