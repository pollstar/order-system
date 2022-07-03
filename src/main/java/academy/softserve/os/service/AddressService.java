package academy.softserve.os.service;

import academy.softserve.os.model.Address;
import academy.softserve.os.service.command.CreateAddressCommand;
import org.springframework.transaction.annotation.Transactional;

public class AddressService {
    @Transactional
    public Address createAddress(CreateAddressCommand createAddressCommand){
        return new Address();
    }
}
