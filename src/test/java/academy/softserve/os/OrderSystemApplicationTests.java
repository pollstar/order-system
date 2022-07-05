package academy.softserve.os;

import academy.softserve.os.api.dto.AddressDTO;
import academy.softserve.os.api.dto.command.CreateAddressCommandDTO;
import academy.softserve.os.api.mapper.AddressMapper;
import academy.softserve.os.model.Address;
import academy.softserve.os.service.command.CreateAddressCommand;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OrderSystemApplicationTests {
    @Test
    public void givenAddressDTOwithtoAddress_whenMaps_thenCorrect() {
        AddressMapper mapper = Mappers.getMapper(AddressMapper.class);
        AddressDTO dto = new AddressDTO();
        dto.setCity("Харьков");
        dto.setStreet("Кацарская");
        dto.setHouse("23");
        dto.setRoom("администрация");

        Address entity = mapper.dtoAddressToAddress(dto);

        assertEquals(dto.getCity(), entity.getCity());
        assertEquals(dto.getStreet(), entity.getStreet());
        assertEquals(dto.getHouse(), entity.getHouse());
        assertEquals(dto.getRoom(), entity.getRoom());
    }

    @Test
    public void givenAddressWithtoAddressDTO_whenMaps_thenCorrect() {
        AddressMapper mapper = Mappers.getMapper(AddressMapper.class);
        Address address = new Address();
        address.setCity("Харьков");
        address.setStreet("Кацарская");
        address.setHouse("23");
        address.setRoom("администрация");

        AddressDTO dto = mapper.addressToAddressDto(address);

        assertEquals(dto.getCity(), address.getCity());
        assertEquals(dto.getStreet(), address.getStreet());
        assertEquals(dto.getHouse(), address.getHouse());
        assertEquals(dto.getRoom(), address.getRoom());
    }

    @Test
    public void givenCreateAddressCommandWithtoCreateAddressCommandDTO_whenMaps_thenCorrect() {
        AddressMapper mapper = Mappers.getMapper(AddressMapper.class);
        CreateAddressCommand command = new CreateAddressCommand();
        command.setCity("Харьков");
        command.setStreet("Кацарская");
        command.setHouse("23");
        command.setRoom("администрация");

        CreateAddressCommandDTO dto = mapper.commandToCommandDTO(command);

        assertEquals(dto.getCity(), command.getCity());
        assertEquals(dto.getStreet(), command.getStreet());
        assertEquals(dto.getHouse(), command.getHouse());
        assertEquals(dto.getRoom(), command.getRoom());
    }


    @Test
    public void givenCreateAddressCommandDTOWithtoCreateAddressCommand_whenMaps_thenCorrect() {
        AddressMapper mapper = Mappers.getMapper(AddressMapper.class);
        CreateAddressCommandDTO dto = new CreateAddressCommandDTO();
        dto.setCity("Харьков");
        dto.setStreet("Кацарская");
        dto.setHouse("23");
        dto.setRoom("администрация");

        CreateAddressCommand command = mapper.commandDtoToCommand(dto);

        assertEquals(command.getStreet(), dto.getStreet());
        assertEquals(command.getHouse(), dto.getHouse());
        assertEquals(command.getCity(), dto.getCity());
        assertEquals(command.getRoom(), dto.getRoom());
    }
}
