package academy.softserve.os.service.impl;

import academy.softserve.os.model.Client;
import academy.softserve.os.repository.ClientRepository;
import academy.softserve.os.service.ClientService;
import academy.softserve.os.service.command.CreateClientCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClientServiceImplTest {

    private ClientRepository clientRepository;
    private ClientService clientService;

    @BeforeEach
    void init() {
        clientRepository = Mockito.mock(ClientRepository.class);
        clientService = new ClientServiceImpl(clientRepository);
    }

    @Test
    void givenValidCreateClientCommand_createClient_shouldReturnCreatedClient() {
        //given
        var createClientCommand = new CreateClientCommand("Pol");
        //when
        when(clientRepository.save(any(Client.class))).then(returnsFirstArg());
        var client = clientService.createClient(createClientCommand);
        //then
        assertEquals("Pol", client.getName());
    }
}