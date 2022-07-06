package academy.softserve.os.service;

import academy.softserve.os.model.Address;
import academy.softserve.os.repository.AddressRepository;
import academy.softserve.os.service.command.CreateAddressCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface AddressService {
    Address createAddress(CreateAddressCommand command);
}
