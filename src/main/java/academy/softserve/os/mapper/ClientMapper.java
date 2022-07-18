package academy.softserve.os.mapper;

import academy.softserve.os.api.dto.ClientDTO;
import academy.softserve.os.api.dto.command.CreateClientCommandDTO;
import academy.softserve.os.model.Client;
import academy.softserve.os.service.command.CreateClientCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    CreateClientCommand toModel(CreateClientCommandDTO clientCommandDTO);

    ClientDTO toDTO(Client client);
}
