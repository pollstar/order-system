package academy.softserve.os.api;

import academy.softserve.os.api.dto.EquipmentDTO;
import academy.softserve.os.api.dto.command.CreateEquipmentCommandDTO;
import academy.softserve.os.exception.CreateEquipmentException;
import academy.softserve.os.mapper.EquipmentMapper;
import academy.softserve.os.model.Address;
import academy.softserve.os.model.Client;
import academy.softserve.os.model.Equipment;
import academy.softserve.os.service.EquipmentService;
import academy.softserve.os.service.command.CreateEquipmentCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = {EquipmentController.class, EquipmentMapper.class})
class EquipmentControllerCreateEquipmentTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EquipmentMapper equipmentMapper;

    @MockBean
    private EquipmentService service;

    private final ObjectMapper mapper = new ObjectMapper();
    private CreateEquipmentCommandDTO commandDTO;

    @BeforeEach
    void setUp() {
        commandDTO = CreateEquipmentCommandDTO.builder()
                .description("Кондиціонер1")
                .clientId(1L)
                .addressId(1L)
                .build();
    }

    @Test
    void givenValidCreateEquipmentCommandDTO_createEquipment_shouldCreateNewEquipmentAndReturnOkResponse() throws Exception {
        //given
        var client = Client.builder()
                .id(commandDTO.getClientId())
                .name("Client")
                .build();
        var adderess = Address.builder()
                .id(commandDTO.getAddressId())
                .city("Lviv")
                .street("Street")
                .house("2")
                .room("1")
                .build();
        var equipment = Equipment.builder()
                .id(1L)
                .description(commandDTO.getDescription())
                .client(client)
                .address(adderess)
                .build();
        //when
        when(service.createEquipment(any(CreateEquipmentCommand.class))).thenReturn(Optional.of(equipment));
        //then
        mockMvc.perform(post("/api/admin/equipment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(commandDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(equipment.getId()))
                .andExpect(jsonPath("$.client.id").value(equipment.getClient().getId()))
                .andExpect(jsonPath("$.client.name").value(equipment.getClient().getName()))
                .andExpect(jsonPath("$.address.id").value(equipment.getAddress().getId()))
                .andExpect(jsonPath("$.address.city").value(equipment.getAddress().getCity()))
                .andExpect(jsonPath("$.address.street").value(equipment.getAddress().getStreet()))
                .andExpect(jsonPath("$.address.house").value(equipment.getAddress().getHouse()))
                .andExpect(jsonPath("$.address.room").value(equipment.getAddress().getRoom()));
    }

    @Test
    void givenCreateNotValidEquipmentCommand_createEquipment_shouldReturnExceptionErrorMessage() throws Exception {
        //given
        //when
        when(service.createEquipment(any(CreateEquipmentCommand.class))).thenThrow(CreateEquipmentException.class);
        //then
        mockMvc.perform(post("/api/admin/equipment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(commandDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenCreateEquipmentCommandDTOWithNullFieldStreet_createEquipment_shouldReturnErrorMessageBecauseFieldStreetCannotBeNull() throws Exception {
        //given
        commandDTO.setClientId(null);
        //when
        when(service.createEquipment(any(CreateEquipmentCommand.class)))
                .thenReturn(Optional.empty());
        //then
        mockMvc.perform(post("/api/admin/equipment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(commandDTO)))
                .andExpect(status().isBadRequest());
    }
}