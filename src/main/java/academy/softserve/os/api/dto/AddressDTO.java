package academy.softserve.os.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private String city;
    private String street;
    private String house;
    private String room;
}
