package academy.softserve.os.api.mapper;

import academy.softserve.os.api.dto.AddressDTO;
import academy.softserve.os.api.dto.command.CreateAddressCommandDTO;
import academy.softserve.os.model.Address;
import academy.softserve.os.service.command.CreateAddressCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toAddress(AddressDTO dto);

    AddressDTO toAddressDTO(Address address);

    CreateAddressCommand toCommand(CreateAddressCommandDTO dto);

    CreateAddressCommandDTO toCommandDTO(CreateAddressCommand command);
}
