package academy.softserve.os.api;

import academy.softserve.os.api.dto.ClientDTO;
import academy.softserve.os.api.dto.command.CreateClientCommandDTO;
import academy.softserve.os.mapper.ClientMapper;
import academy.softserve.os.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper mapper;

    @PostMapping
    @Transactional
    public ResponseEntity<ClientDTO> createClient(@RequestBody @Valid CreateClientCommandDTO clientCommandDTO) {
        var client = clientService.createClient(mapper.toModel(clientCommandDTO));
        var clientDto = mapper.toDTO(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientDto);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<ClientDTO> findClientById(@PathVariable Long id) {
        return clientService.findClientById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    @Transactional
    public ResponseEntity<List<ClientDTO>> findAllClientsByName(@RequestParam String name) {
        var clients = clientService.findAllClientsByName(name).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clients);
    }
}
