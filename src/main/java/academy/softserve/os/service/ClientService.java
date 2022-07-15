package academy.softserve.os.service;

import academy.softserve.os.model.Client;
import academy.softserve.os.service.command.CreateClientCommand;

import java.util.List;

public interface ClientService {

    Client createClient(CreateClientCommand command);

    Client findClientById(Long id);

    List<Client> findAllClientsByName(String name);
}
