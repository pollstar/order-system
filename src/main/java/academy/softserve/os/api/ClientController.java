package academy.softserve.os.api;

import academy.softserve.os.api.dto.ClientDTO;
import academy.softserve.os.api.dto.command.CreateClientCommandDTO;
import academy.softserve.os.mapper.ClientMapper;
import academy.softserve.os.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper mapper;

    @PostMapping
    @Transactional
    public ResponseEntity<ClientDTO> createClient(@RequestBody @Valid CreateClientCommandDTO clientCommandDTO){
        var client = clientService.createClient(mapper.toModel(clientCommandDTO));
        var clientDto = mapper.toDTO(client);
        return Optional.of(clientDto)
                .map(clientDTO -> ResponseEntity.status(HttpStatus.CREATED).body(clientDTO))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
