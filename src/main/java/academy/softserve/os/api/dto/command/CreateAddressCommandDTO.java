package academy.softserve.os.api.dto.command;

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
public class CreateAddressCommandDTO {
    private String city;
    private String street;
    private String house;
    private String room;
}
