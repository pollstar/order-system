package academy.softserve.os.service;

import academy.softserve.os.model.Address;
import academy.softserve.os.repository.AddressRepository;
import academy.softserve.os.service.command.CreateAddressCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public Address createAddress(CreateAddressCommand createAddressCommand){
        return addressRepository.saveAndFlush(createAddressCommand.getAddress());
    }
}
