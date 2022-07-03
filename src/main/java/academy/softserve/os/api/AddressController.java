package academy.softserve.os.api;

import academy.softserve.os.api.dto.AddressDTO;
import academy.softserve.os.api.dto.command.CreateAddressCommandDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class AddressController {
    @Transactional
    @PostMapping("/admin/address")
    public ResponseEntity<AddressDTO> createAddress(@RequestBody CreateAddressCommandDTO commandDTO){
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity(commandDTO.getCity().toUpperCase(Locale.ROOT));
        addressDTO.setStreet(commandDTO.getStreet().toUpperCase(Locale.ROOT));
        addressDTO.setHouse(commandDTO.getHouse().toUpperCase(Locale.ROOT));
        addressDTO.setRoom(commandDTO.getRoom().toUpperCase(Locale.ROOT));

        return new ResponseEntity<>(addressDTO, HttpStatus.CREATED);
    }
}
