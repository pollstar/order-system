package academy.softserve.os.service;

import academy.softserve.os.model.Client;
import academy.softserve.os.service.command.CreateClientCommand;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Client createClient(CreateClientCommand command);

   Optional<Client> findClientById(Long id);

    List<Client> findAllClientsByName(String name);
}
