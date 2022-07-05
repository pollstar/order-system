package academy.softserve.os.api.mapper;

import academy.softserve.os.api.dto.AddressDTO;
import academy.softserve.os.api.dto.command.CreateAddressCommandDTO;
import academy.softserve.os.model.Address;
import academy.softserve.os.service.command.CreateAddressCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address dtoAddressToAddress(AddressDTO dto);
    AddressDTO addressToAddressDto(Address address);

    CreateAddressCommand commandDtoToCommand(CreateAddressCommandDTO dto);
    CreateAddressCommandDTO commandToCommandDTO(CreateAddressCommand command);

}
