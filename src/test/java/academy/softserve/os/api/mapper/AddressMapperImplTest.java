package academy.softserve.os.api.mapper;

import academy.softserve.os.api.dto.AddressDTO;
import academy.softserve.os.api.dto.command.CreateAddressCommandDTO;
import academy.softserve.os.mapper.AddressMapper;
import academy.softserve.os.model.Address;
import academy.softserve.os.service.command.CreateAddressCommand;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressMapperImplTest {
    private final AddressMapper mapper = Mappers.getMapper(AddressMapper.class);

    @Test
    void givenAddressDTO_toAddress_shouldCorrectlyMapToAddress() {

        var addressDTO = AddressDTO.builder()
                .id(1L)
                .city(" Харків")
                .house("Сумска  вул.")
                .street("буд.23 ")
                .room("вітальня")
                .build();

        var address = mapper.toAddress(addressDTO);

        assertEquals(addressDTO.getId(), address.getId());
        assertEquals(addressDTO.getCity(), address.getCity());
        assertEquals(addressDTO.getStreet(), address.getStreet());
        assertEquals(addressDTO.getHouse(), address.getHouse());
        assertEquals(addressDTO.getRoom(), address.getRoom());
    }

    @Test
    void givenAddress_toAddressDTO_shouldCorrectlyMapToAddressDTO() {

        var address = Address.builder()
                .id(1L)
                .city(" Харків")
                .house("Сумска  вул.")
                .street("буд.23 ")
                .room("вітальня")
                .build();

        var addressDTO = mapper.toAddressDTO(address);

        assertEquals(addressDTO.getId(), address.getId());
        assertEquals(addressDTO.getCity(), address.getCity());
        assertEquals(addressDTO.getStreet(), address.getStreet());
        assertEquals(addressDTO.getHouse(), address.getHouse());
        assertEquals(addressDTO.getRoom(), address.getRoom());
    }

    @Test
    void givenCreateAddressCommandDTO_toCommand_shouldCorrectlyMapToCreateAddressCommand() {

        var commandDTO = CreateAddressCommandDTO.builder()
                .city(" Харків")
                .house("Сумска  вул.")
                .street("буд.23 ")
                .room("вітальня")
                .build();

        var command = mapper.toCommand(commandDTO);

        assertEquals(commandDTO.getCity(),   command.getCity());
        assertEquals(commandDTO.getStreet(), command.getStreet());
        assertEquals(commandDTO.getHouse(),  command.getHouse());
        assertEquals(commandDTO.getRoom(),   command.getRoom());
    }

    @Test
    void givenCreateAddressCommand_toCommandDTO_shouldCorrectlyMapToCreateAddressCommandDTO() {

        var command = CreateAddressCommand.builder()
                .city(" Харків")
                .house("Сумска  вул.")
                .street("буд.23 ")
                .room("вітальня")
                .build();

        var commandDTO = mapper.toCommandDTO(command);

        assertEquals(command.getCity(),   commandDTO.getCity());
        assertEquals(command.getStreet(), commandDTO.getStreet());
        assertEquals(command.getHouse(),  commandDTO.getHouse());
        assertEquals(command.getRoom(),   commandDTO.getRoom());
    }
}