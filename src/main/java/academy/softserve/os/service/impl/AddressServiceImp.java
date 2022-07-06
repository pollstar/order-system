package academy.softserve.os.service.impl;

import academy.softserve.os.model.Address;
import academy.softserve.os.repository.AddressRepository;
import academy.softserve.os.service.AddressService;
import academy.softserve.os.service.command.CreateAddressCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressServiceImp implements AddressService {
    private AddressRepository addressRepository;

    @Transactional
    public Address createAddress(CreateAddressCommand command){
        return addressRepository.saveAndFlush(command.getAddress());
    }
}
