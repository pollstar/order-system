package academy.softserve.os.mapper;

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

        var equipmentDTO = mapper.toEquipmentDTO(equipment);

        assertEquals(equipment.getId(), equipmentDTO.getId());
        assertEquals(equipment.getClient().getName(), equipmentDTO.getClient().getName());
        assertEquals(equipment.getAddress().getCity(), equipmentDTO.getAddress().getCity());
    }

    @Test
    void givenCreateEquipmentCommandDTO_mapperToCommand_shouldReturnCreateCommandEquipment() {

        var commandDto = CreateEquipmentCommandDTO.builder()
                .addressId(1L)
                .clientId(2L)
                .description("Description")
                .build();

        var command = mapper.toCommand(commandDto);

        assertEquals(commandDto.getDescription(), command.getDescription());
        assertEquals(commandDto.getAddressId(), command.getAddressId());
        assertEquals(commandDto.getClientId(), command.getClientId());
    }
}