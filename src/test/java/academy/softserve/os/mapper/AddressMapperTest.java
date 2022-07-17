package academy.softserve.os.mapper;

import academy.softserve.os.api.dto.AddressDTO;
import academy.softserve.os.api.dto.command.CreateAddressCommandDTO;
import academy.softserve.os.model.Address;
import academy.softserve.os.service.command.CreateAddressCommand;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressMapperTest {

    private final AddressMapper mapper = Mappers.getMapper(AddressMapper.class);

    @Test
    void givenAddressDTO_mapperToAddress_shouldReturnAddress() {
        //given
        var dto = AddressDTO.builder()
                .id(1L)
                .city("City")
                .street("Street")
                .house("House")
                .room("room")
                .build();
        //when
        var address = mapper.toAddress(dto);
        //then
        assertEquals(dto.getId(), address.getId());
        assertEquals(dto.getCity(), address.getCity());
        assertEquals(dto.getStreet(), address.getStreet());
        assertEquals(dto.getHouse(), address.getHouse());
        assertEquals(dto.getRoom(), address.getRoom());
    }

    @Test
    void givenAddress_mapperToAddressDTO_shouldReturnAddressDTO() {
        //given
        var address = Address.builder()
                .id(1L)
                .city("City")
                .street("Street")
                .house("House")
                .room("room")
                .build();
        //when
        var dto = mapper.toAddressDTO(address);
        //then
        assertEquals(address.getId(), dto.getId());
        assertEquals(address.getCity(), dto.getCity());
        assertEquals(address.getStreet(), dto.getStreet());
        assertEquals(address.getHouse(), dto.getHouse());
        assertEquals(address.getRoom(),dto.getRoom());
    }

    @Test
    void givenCreateAddressCommandDTO_mapperToCreateAddressCommand_shouldReturnCreateAddressCommand() {
        //given
        var commandDto = CreateAddressCommandDTO.builder()
                .city("City")
                .street("Street")
                .house("House")
                .room("room")
                .build();
        //when
        var command = mapper.toCommand(commandDto);
        //then
        assertEquals(command.getCity(), commandDto.getCity());
        assertEquals(command.getStreet(), commandDto.getStreet());
        assertEquals(command.getHouse(), commandDto.getHouse());
        assertEquals(command.getRoom(), commandDto.getRoom());
    }

    @Test
    void givenCreateAddressCommand_mapperToCreateAddressCommandDTO_shouldReturnCreateAddressCommandDTO() {
        //given
        var command = CreateAddressCommand.builder()
                .city("City")
                .street("Street")
                .house("House")
                .room("room")
                .build();
        //when
        var commandDto = mapper.toCommandDTO(command);
        //then
        assertEquals(command.getCity(), commandDto.getCity());
        assertEquals(command.getStreet(), commandDto.getStreet());
        assertEquals(command.getHouse(), commandDto.getHouse());
        assertEquals(command.getRoom(), commandDto.getRoom());
    }
}
