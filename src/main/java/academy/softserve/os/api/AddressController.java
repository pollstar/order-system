package academy.softserve.os.api;

import academy.softserve.os.api.dto.AddressDTO;
import academy.softserve.os.api.dto.command.CreateAddressCommandDTO;
import academy.softserve.os.api.mapper.AddressMapper;
import academy.softserve.os.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AddressController {
    private final AddressMapper mapper;
    private final AddressService addressService;

    @Transactional
    @PostMapping("/admin/address")
    public ResponseEntity<AddressDTO> createAddress(@RequestBody CreateAddressCommandDTO commandDTO) {
        return new ResponseEntity<>(mapper.toAddressDTO(addressService.createAddress(mapper.toCommand(commandDTO))),
                HttpStatus.CREATED);
    }

    @GetMapping("/address")
    public ResponseEntity<List<AddressDTO>> getAddress() {
        var addresses = addressService.findAddresses().stream()
                .map(mapper::toAddressDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {
        return addressService.getAddressById(id)
                .map(address -> new ResponseEntity<>(mapper.toAddressDTO(address), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
