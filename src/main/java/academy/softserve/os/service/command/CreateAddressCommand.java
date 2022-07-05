package academy.softserve.os.service.command;

import academy.softserve.os.model.Address;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class CreateAddressCommand {
    private String city;
    private String street;
    private String house;
    private String room;

    public Address getAddress() {
        return new Address(null, city, street, house, room, null);
    }
}
