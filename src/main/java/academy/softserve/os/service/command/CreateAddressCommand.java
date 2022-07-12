package academy.softserve.os.service.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateAddressCommand {
    private String city;
    private String street;
    private String house;
    private String room;
}
