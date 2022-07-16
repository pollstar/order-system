package academy.softserve.os.api;

import academy.softserve.os.api.dto.command.CreateEquipmentCommandDTO;
import academy.softserve.os.exception.CreateEquipmentException;
import academy.softserve.os.mapper.EquipmentMapper;
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
        var equipment = Equipment.builder()
                .id(1L)
                .description("Кондиціонер1")
//                .clientId(1L)
//                .addressId(1L)
                .build();
        //when
        when(service.createEquipment(any(CreateEquipmentCommand.class))).thenReturn(Optional.of(equipment));
        //then
        mockMvc.perform(post("/api/admin/equipment")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(commandDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.city").value("ХАРЬКОВ"))
                .andExpect(jsonPath("$.street").value("СУМСКАЯ"))
                .andExpect(jsonPath("$.house").value("10"))
                .andExpect(jsonPath("$.room").value("КУХНЯ"));

    }

    @Test
    void givenCreateEquipmentCommandDTOWithNullFieldCity_createEquipment_shouldReturnErrorMessageBecauseFieldCityCannotBeNull() throws Exception {
        //given
        commandDTO.setDescription("");
        //when
        when(service.createEquipment(any(CreateEquipmentCommand.class))).thenThrow(CreateEquipmentException.class);
        //then
        mockMvc.perform(post("/api/admin/equipment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(commandDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error created equipment"));
    }

    @Test
    void givenCreateEquipmentCommandDTOWithNullFieldStreet_createEquipment_shouldReturnErrorMessageBecauseFieldStreetCannotBeNull() throws Exception {
        //given
        commandDTO.setClientId(null);
        //when
        when(service.createEquipment(any(CreateEquipmentCommand.class))).thenThrow(CreateEquipmentException.class);
        //then
        mockMvc.perform(post("/api/admin/equipment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(commandDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error created equipment"));
    }

    @Test
    void givenCreateEquipmentCommandDTOWithNullFieldHouse_createEquipment_shouldReturnErrorMessageBecauseFieldHouseCannotBeNull() throws Exception {
        //given
        commandDTO.setAddressId(null);
        //when
        when(service.createEquipment(any(CreateEquipmentCommand.class))).thenThrow(CreateEquipmentException.class);
        //then
        mockMvc.perform(post("/api/admin/equipment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(commandDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error created equipment"));
    }
}