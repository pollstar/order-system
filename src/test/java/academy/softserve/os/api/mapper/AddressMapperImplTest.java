package academy.softserve.os.api.mapper;

import academy.softserve.os.api.dto.AddressDTO;
import academy.softserve.os.api.dto.command.CreateAddressCommandDTO;
import academy.softserve.os.model.Address;
import academy.softserve.os.service.command.CreateAddressCommand;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressMapperImplTest {
    private final AddressMapper mapper = Mappers.getMapper(AddressMapper.class);

    @Test
    void givenAddressDTO_toAddress_shouldCorrectlyMapToAddress() {
        //given
        var addressDTO = AddressDTO.builder()
                .id(1L)
                .city(" Харків")
                .house("Сумска  вул.")
                .street("буд.23 ")
                .room("вітальня")
                .build();
        //when
        var address = mapper.toAddress(addressDTO);
        //then
        assertEquals(addressDTO.getId(), address.getId());
        assertEquals(addressDTO.getCity(), address.getCity());
        assertEquals(addressDTO.getStreet(), address.getStreet());
        assertEquals(addressDTO.getHouse(), address.getHouse());
        assertEquals(addressDTO.getRoom(), address.getRoom());
    }

    @Test
    void givenAddress_toAddressDTO_shouldCorrectlyMapToAddressDTO() {
        //given
        var address = Address.builder()
                .id(1L)
                .city(" Харків")
                .house("Сумска  вул.")
                .street("буд.23 ")
                .room("вітальня")
                .build();
        //when
        var addressDTO = mapper.toAddressDTO(address);
        //then
        assertEquals(addressDTO.getId(), address.getId());
        assertEquals(addressDTO.getCity(), address.getCity());
        assertEquals(addressDTO.getStreet(), address.getStreet());
        assertEquals(addressDTO.getHouse(), address.getHouse());
        assertEquals(addressDTO.getRoom(), address.getRoom());
    }

    @Test
    void givenCreateAddressCommandDTO_toCommand_shouldCorrectlyMapToCreateAddressCommand() {
        //given
        var commandDTO = CreateAddressCommandDTO.builder()
                .city(" Харків")
                .house("Сумска  вул.")
                .street("буд.23 ")
                .room("вітальня")
                .build();
        //when
        var command = mapper.toCommand(commandDTO);
        //then
        assertEquals(commandDTO.getCity(),   command.getCity());
        assertEquals(commandDTO.getStreet(), command.getStreet());
        assertEquals(commandDTO.getHouse(),  command.getHouse());
        assertEquals(commandDTO.getRoom(),   command.getRoom());
    }

    @Test
    void givenCreateAddressCommand_toCommandDTO_shouldCorrectlyMapToCreateAddressCommandDTO() {
        //given
        var command = CreateAddressCommand.builder()
                .city(" Харків")
                .house("Сумска  вул.")
                .street("буд.23 ")
                .room("вітальня")
                .build();
        //when
        var commandDTO = mapper.toCommandDTO(command);
        //then
        assertEquals(command.getCity(),   commandDTO.getCity());
        assertEquals(command.getStreet(), commandDTO.getStreet());
        assertEquals(command.getHouse(),  commandDTO.getHouse());
        assertEquals(command.getRoom(),   commandDTO.getRoom());
    }
}