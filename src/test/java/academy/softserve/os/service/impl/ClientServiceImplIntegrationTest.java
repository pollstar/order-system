package academy.softserve.os.service.impl;

import academy.softserve.os.service.ClientService;
import academy.softserve.os.service.command.CreateClientCommand;
import academy.softserve.os.service.exception.ClientNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ClientServiceImplIntegrationTest {

    @Autowired
    private ClientService clientService;

    @Test
    void givenClientName_findClientByName_shouldReturnListOfClientsWithMatchingName() {
        var client1 = new CreateClientCommand("PolRon");
        var client2 = new CreateClientCommand("Pol");
        var client3 = new CreateClientCommand("Ron");
        var client4 = new CreateClientCommand("Ron Poll");
        clientService.createClient(client1);
        clientService.createClient(client2);
        clientService.createClient(client3);
        clientService.createClient(client4);
        var clients = clientService.findAllClientsByName("pol");
        assertEquals(3, clients.size());
    }

    @Test
    void givenClientId_findClientById_shouldReturnClientWithGivenId() {

        var client = new CreateClientCommand("Pol");
        var id = clientService.createClient(client).getId();

        var clientById = clientService.findClientById(id);

        assertEquals(client.getName(), clientById.getName());
    }

    @Test
    void givenClientId_findClientById_notFoundShouldBeThrowException() {
        Long id = 25L;
        var client = new CreateClientCommand("Pol");
        var clientId = clientService.createClient(client).getId();

        assertNotEquals(id, clientId);
        assertThrows(ClientNotFoundException.class, () -> clientService.findClientById(id));
    }
}
