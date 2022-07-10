package academy.softserve.os.api.mapper;

import academy.softserve.os.api.dto.AddressDTO;
import academy.softserve.os.api.dto.command.CreateAddressCommandDTO;
import academy.softserve.os.model.Address;
import academy.softserve.os.service.command.CreateAddressCommand;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class AddressMapperImplTest {
    private final AddressMapper mapper = Mappers.getMapper(AddressMapper.class);

    @Test
    void givenAddressDTO_toAddress_shouldCorrectAddress() {
        //given
        AddressDTO addressDTO = AddressDTO.builder()
                .id(1L)
                .city(" Харків")
                .house("Сумска  вул.")
                .street("буд.23 ")
                .room("вітальня")
                .build();
        //when
        Address address = mapper.toAddress(addressDTO);
        //then
        assertEquals(addressDTO.getId(), address.getId());
        assertEquals(addressDTO.getCity(), address.getCity());
        assertEquals(addressDTO.getStreet(), address.getStreet());
        assertEquals(addressDTO.getHouse(), address.getHouse());
        assertEquals(addressDTO.getRoom(), address.getRoom());
    }

    @Test
    void givenAddress_toAddressDTO_shouldCorrectAddressDTO() {
        //given
        Address address = Address.builder()
                .id(1L)
                .city(" Харків")
                .house("Сумска  вул.")
                .street("буд.23 ")
                .room("вітальня")
                .build();
        //when
        AddressDTO addressDTO = mapper.toAddressDTO(address);
        //then
        assertEquals(addressDTO.getId(), address.getId());
        assertEquals(addressDTO.getCity(), address.getCity());
        assertEquals(addressDTO.getStreet(), address.getStreet());
        assertEquals(addressDTO.getHouse(), address.getHouse());
        assertEquals(addressDTO.getRoom(), address.getRoom());
    }

    @Test
    void givenCreateAddressCommandDTO_toCommand_shouldCorrectCreateAddressCommand() {
        //given
        CreateAddressCommandDTO commandDTO = CreateAddressCommandDTO.builder()
                .city(" Харків")
                .house("Сумска  вул.")
                .street("буд.23 ")
                .room("вітальня")
                .build();
        //when
        CreateAddressCommand command = mapper.toCommand(commandDTO);
        //then
        assertEquals(commandDTO.getCity(),   command.getCity());
        assertEquals(commandDTO.getStreet(), command.getStreet());
        assertEquals(commandDTO.getHouse(),  command.getHouse());
        assertEquals(commandDTO.getRoom(),   command.getRoom());
    }

    @Test
    void givenCreateAddressCommand_toCommandDTO_shouldCorrectCreateAddressCommandDTO() {
        //given
        CreateAddressCommand command = CreateAddressCommand.builder()
                .city(" Харків")
                .house("Сумска  вул.")
                .street("буд.23 ")
                .room("вітальня")
                .build();
        //when
        CreateAddressCommandDTO commandDTO = mapper.toCommandDTO(command);
        //then
        assertEquals(command.getCity(),   commandDTO.getCity());
        assertEquals(command.getStreet(), commandDTO.getStreet());
        assertEquals(command.getHouse(),  commandDTO.getHouse());
        assertEquals(command.getRoom(),   commandDTO.getRoom());
    }
}