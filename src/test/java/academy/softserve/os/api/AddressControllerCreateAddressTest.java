package academy.softserve.os.api;

import academy.softserve.os.api.dto.command.CreateAddressCommandDTO;
import academy.softserve.os.api.mapper.AddressMapper;
import academy.softserve.os.service.exception.CreateAddressException;
import academy.softserve.os.model.Address;
import academy.softserve.os.service.AddressService;
import academy.softserve.os.service.command.CreateAddressCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = {AddressController.class, AddressMapper.class})
class AddressControllerCreateAddressTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService service;

    private final ObjectMapper mapper = new ObjectMapper();
    private CreateAddressCommandDTO commandDTO;

    @BeforeEach
    void setUp() {
        commandDTO = CreateAddressCommandDTO.builder()
                .city("Харьков")
                .street("Сумская")
                .house("10")
                .room("кухня")
                .build();
    }

    @Test
    void givenValidCreateAddressCommandDTO_createAddress_shouldCreateNewAddressAndReturnOkResponse() throws Exception {
        //given
        Address address = Address.builder()
                .id(1L)
                .city("ХАРЬКОВ")
                .street("СУМСКАЯ")
                .house("10")
                .room("КУХНЯ")
                .build();
        //when
        when(service.createAddress(any(CreateAddressCommand.class))).thenReturn(address);
        //then
        mockMvc.perform(post("/api/admin/address")
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
    void givenCreateAddressCommandDTOWithNullFieldCity_createAddress_shouldReturnErrorMessageBecauseFieldCityCannotBeNull() throws Exception {
        //given
        commandDTO.setCity("");
        //when
        when(service.createAddress(any(CreateAddressCommand.class))).thenThrow(CreateAddressException.class);
        //then
        mockMvc.perform(post("/api/admin/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(commandDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error created address"));
    }

    @Test
    void givenCreateAddressCommandDTOWithNullFieldStreet_createAddress_shouldReturnErrorMessageBecauseFieldStreetCannotBeNull() throws Exception {
        //given
        commandDTO.setStreet("");
        //when
        when(service.createAddress(any(CreateAddressCommand.class))).thenThrow(CreateAddressException.class);
        //then
        mockMvc.perform(post("/api/admin/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(commandDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error created address"));
    }

    @Test
    void givenCreateAddressCommandDTOWithNullFieldHouse_createAddress_shouldReturnErrorMessageBecauseFieldHouseCannotBeNull() throws Exception {
        //given
        commandDTO.setHouse("");
        //when
        when(service.createAddress(any(CreateAddressCommand.class))).thenThrow(CreateAddressException.class);
        //then
        mockMvc.perform(post("/api/admin/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(commandDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error created address"));
    }
}