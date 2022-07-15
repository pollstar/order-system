package academy.softserve.os.api;


import academy.softserve.os.api.dto.command.CreateClientCommandDTO;
import academy.softserve.os.mapper.ClientMapper;
import academy.softserve.os.model.Client;
import academy.softserve.os.service.ClientService;
import academy.softserve.os.service.command.CreateClientCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebMvcTest(value = {ClientController.class, ClientMapper.class})
class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClientService clientService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenValidCreateClientCommandDTO_createClient_shouldCreateNewClientAndReturnOKResponse() throws Exception {
        var client = Client.builder()
                .id(1L)
                .name("Pol")
                .build();
        var createCommandClientDTO = new CreateClientCommandDTO("Pol");
        when(clientService.createClient(any(CreateClientCommand.class))).thenReturn(client);

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCommandClientDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Pol"));
    }

    @Test
    void givenCreateClientCommandDTOWithNullName_createClient_shouldReturnErrorMessageBecauseNameCannotBeNull() throws Exception{
        var createCommandDto =new  CreateClientCommandDTO(null);

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCommandDto)))
                //then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed!"));

    }

    @Test
    void givenCreateClientCommandDTOWithEmptyName_createClient_shouldReturnErrorMessageBecauseNameCannotBeEmpty() throws Exception{
        var createCommandDto =new  CreateClientCommandDTO("");

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCommandDto)))
                //then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed!"));

    }
}