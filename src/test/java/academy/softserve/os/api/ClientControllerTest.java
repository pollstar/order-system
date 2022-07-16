package academy.softserve.os.api;


import academy.softserve.os.api.dto.command.CreateClientCommandDTO;
import academy.softserve.os.mapper.ClientMapper;
import academy.softserve.os.model.Client;
import academy.softserve.os.service.ClientService;
import academy.softserve.os.service.command.CreateClientCommand;
import academy.softserve.os.service.exception.ClientNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {ClientController.class, ClientMapper.class})
class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClientService clientService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenValidCreateClientCommandDTO_createClient_shouldCreateNewClientAndReturnOKResponse() throws Exception {
        //given
        var client = Client.builder()
                .id(1L)
                .name("Pol")
                .build();
        var createCommandClientDTO = new CreateClientCommandDTO("Pol");
        //when
        when(clientService.createClient(any(CreateClientCommand.class))).thenReturn(client);
        //then
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCommandClientDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Pol"));
    }

    @Test
    void givenCreateClientCommandDTOWithNullName_createClient_shouldReturnErrorMessageBecauseNameCannotBeNull() throws Exception {
        //given
        var createCommandDto = new CreateClientCommandDTO(null);
        //when
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCommandDto)))
                //then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed!"));
    }

    @Test
    void givenCreateClientCommandDTOWithEmptyName_createClient_shouldReturnErrorMessageBecauseNameCannotBeEmpty() throws Exception {
        //given
        var createCommandDto = new CreateClientCommandDTO("");
        //when
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCommandDto)))
                //then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed!"));
    }

    @Test
    void givenClientId_findClientById_shouldReturnClient() throws Exception {
        //given
        var client = Client.builder()
                .id(1L)
                .name("Pol").build();
        //when
        when(clientService.findClientById(any(Long.class))).thenReturn(Optional.of(client));

        mockMvc.perform(get("/api/clients/1"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Pol"));
    }

    @Test
    void givenClientId_findClientById_shouldThrowExceptionBecauseNoClientWithSuchIdExists() throws Exception {
        //when
        when(clientService.findClientById(any(Long.class))).thenThrow(ClientNotFoundException.class);

        mockMvc.perform(get("/api/clients/1"))
                //then
                .andExpect(status().isNotFound());
    }

    @Test
    void givenClientName_findAllClientsByName_shouldReturnAllClientsMatchesByName() throws Exception {
        //given
        var clients = List.of(
                new Client(),
                new Client(),
                new Client()
        );
        //when
        when(clientService.findAllClientsByName(anyString())).thenReturn(clients);

        mockMvc.perform(get("/api/clients?name=Pol"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)));
    }
}
