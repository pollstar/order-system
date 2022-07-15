package academy.softserve.os.service.impl;

import academy.softserve.os.model.Client;
import academy.softserve.os.repository.ClientRepository;
import academy.softserve.os.service.ClientService;
import academy.softserve.os.service.command.CreateClientCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;


    @Override
    @Transactional
    public Client createClient(CreateClientCommand command) {
        var client = Client.builder()
                .name(command.getName())
                .build();

        return clientRepository.save(client);
    }
}
