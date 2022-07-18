package academy.softserve.os.service.impl;

import academy.softserve.os.service.ClientService;
import academy.softserve.os.service.command.CreateClientCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class ClientServiceImplIntegrationTest {

    @Autowired
    private ClientService clientService;
    private CreateClientCommand client;

    @BeforeEach
    void init() {
        client = new CreateClientCommand("Sam");
        clientService.createClient(new CreateClientCommand("PolRon"));
        clientService.createClient(new CreateClientCommand("Pol"));
        clientService.createClient(new CreateClientCommand("Ron"));
        clientService.createClient(new CreateClientCommand("Ron Poll"));
    }



    @Test
    void givenClientName_findClientByName_shouldReturnListOfClientsWithMatchingName() {
        var clients = clientService.findAllClientsByName("Pol");

        assertEquals(3, clients.size());
    }

    @Test
    void givenClientId_findClientById_shouldReturnClientWithGivenId() {

        var id = clientService.createClient(client).getId();

        var clientById = clientService.findClientById(id)
                .orElseThrow();

        assertEquals(client.getName(), clientById.getName());
    }
}
