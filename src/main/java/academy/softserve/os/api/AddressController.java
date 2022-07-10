package academy.softserve.os.api;

import academy.softserve.os.api.dto.AddressDTO;
import academy.softserve.os.api.dto.command.CreateAddressCommandDTO;
import academy.softserve.os.api.mapper.AddressMapper;
import academy.softserve.os.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AddressController {
    private final AddressMapper mapper;
    private final AddressService addressService;

    @Transactional
    @PostMapping("/admin/address")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody CreateAddressCommandDTO commandDTO) {
        return new ResponseEntity<>(mapper.toAddressDTO(addressService.createAddress(mapper.toCommand(commandDTO))),
                HttpStatus.CREATED);
    }
}
