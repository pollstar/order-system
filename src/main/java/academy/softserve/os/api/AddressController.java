package academy.softserve.os.api;

import academy.softserve.os.api.dto.AddressDTO;
import academy.softserve.os.api.dto.command.CreateAddressCommandDTO;
import academy.softserve.os.api.mapper.AddressMapper;
import academy.softserve.os.service.AddressService;
import academy.softserve.os.service.command.CreateAddressCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {
    @Autowired
    private CreateAddressCommand createAddressCommand;
    @Autowired
    private AddressMapper mapper;
    @Autowired
    private AddressService addressService;

    @Transactional
    @PostMapping("/admin/address")
    public ResponseEntity<AddressDTO> createAddress(@RequestBody CreateAddressCommandDTO commandDTO){
        createAddressCommand = mapper.commandDtoToCommand(commandDTO);
        AddressDTO addressDTO = mapper.addressToAddressDto(addressService.createAddress(createAddressCommand));
        return new ResponseEntity<>(addressDTO, HttpStatus.CREATED);
    }
}
