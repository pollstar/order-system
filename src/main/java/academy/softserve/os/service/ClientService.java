package academy.softserve.os.service;

import academy.softserve.os.model.Client;
import academy.softserve.os.service.command.CreateClientCommand;

public interface ClientService {
    Client createClient(CreateClientCommand command);
}
