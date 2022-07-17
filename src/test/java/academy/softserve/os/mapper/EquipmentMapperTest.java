package academy.softserve.os.mapper;

import academy.softserve.os.api.dto.AddressDTO;
import academy.softserve.os.api.dto.command.CreateEquipmentCommandDTO;
import academy.softserve.os.model.Address;
import academy.softserve.os.model.Client;
import academy.softserve.os.model.Equipment;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class EquipmentMapperTest {
    private EquipmentMapper mapper = Mappers.getMapper(EquipmentMapper.class);

    @Test
    void givenEquipment_mapperToEquipmentDTO_shouldReturnEquipmentDTO() {
        //given
        var client = Client.builder()
                .id(1L)
                .name("Client")
                .build();
        var adderess = Address.builder()
                .id(1L)
                .city("Lviv")
                .street("Street")
                .house("2")
                .room("1")
                .build();
        var equipment = Equipment.builder()
                .id(1L)
                .description("Equipment")
                .client(client)
                .address(adderess)
                .build();
        //when
        var equipmentDto = mapper.toEquipmentDTO(equipment);
        //then
        assertEquals(equipment.getId(), equipmentDto.getId());
        assertEquals(equipment.getClient(), equipmentDto.getClient());
        assertEquals(equipment.getAddress(), equipmentDto.getAddress());
    }

    @Test
    void givenCreateEquipmentCommandDTO_mapperToCommand_shouldReturnCreateCommandEquipment() {
        //given
        var commandDto = CreateEquipmentCommandDTO.builder()
                .addressId(1L)
                .clientId(2L)
                .description("Description")
                .build();
        //when
        var command = mapper.toCommand(commandDto);
        //then
        assertEquals(commandDto.getDescription(), command.getDescription());
        assertEquals(commandDto.getAddressId(), command.getAddressId());
        assertEquals(commandDto.getClientId(), command.getClientId());
    }
}