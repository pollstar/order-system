package academy.softserve.os.mapper;

import academy.softserve.os.api.dto.AddressDTO;
import academy.softserve.os.api.dto.ClientDTO;
import academy.softserve.os.api.dto.EquipmentDTO;
import academy.softserve.os.api.dto.command.CreateEquipmentCommandDTO;
import academy.softserve.os.model.Address;
import academy.softserve.os.model.Client;
import academy.softserve.os.model.Equipment;
import academy.softserve.os.service.command.CreateEquipmentCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {ClientMapper.class, AddressMapper.class})
public interface `EquipmentMapper {
    @Mapping(source = "client", target = "client", qualifiedByName = "clientToClientDto")
    @Mapping(source = "address", target = "address", qualifiedByName = "addressToAddressDto")
    EquipmentDTO toEquipmentDTO(Equipment equipment);

    @Named("clientToClientDto")
    static ClientDTO clientToClientDto(Client client) {
        var mapper = Mappers.getMapper(ClientMapper.class);
        return mapper.toDTO(client);
    }

    @Named("addressToAddressDto")
    static AddressDTO addressToAddressDto(Address address) {
        var mapper = Mappers.getMapper(AddressMapper.class);
        return mapper.toAddressDTO(address);
    }

    CreateEquipmentCommand toCommand(CreateEquipmentCommandDTO dto);
}
