package academy.softserve.os.service.impl;

import academy.softserve.os.model.Client;
import academy.softserve.os.repository.ClientRepository;
import academy.softserve.os.service.ClientService;
import academy.softserve.os.service.command.CreateClientCommand;
import academy.softserve.os.service.exception.ClientNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public Client findClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(ClientNotFoundException::new);
    }

    @Override
    public List<Client> findAllClientsByName(String name) {
        return clientRepository.findByNameContainingIgnoreCase(name);
    }
}
