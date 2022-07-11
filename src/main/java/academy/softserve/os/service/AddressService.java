package academy.softserve.os.service;

import academy.softserve.os.service.exception.CreateAddressException;
import academy.softserve.os.model.Address;
import academy.softserve.os.repository.AddressRepository;
import academy.softserve.os.service.command.CreateAddressCommand;
import academy.softserve.os.service.exception.GetAddressByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    @Transactional
    public Address createAddress(CreateAddressCommand command) {

        checkToValidAddress(command);

        UnaryOperator<String> removingExtraSpaces = s -> s.toUpperCase().replaceAll("\\s+", " ").trim();
        var address = Address.builder()
                .city(removingExtraSpaces.apply(command.getCity()))
                .street(removingExtraSpaces.apply(command.getStreet()))
                .house(removingExtraSpaces.apply(command.getHouse()))
                .room(removingExtraSpaces.apply(command.getRoom()))
                .build();


        return addressRepository.findByCityAndStreetAndHouseAndRoom(
                address.getCity(),
                address.getStreet(),
                address.getHouse(),
                address.getRoom()
        ).orElseGet(() -> addressRepository.save(address));
    }

    private void checkToValidAddress(CreateAddressCommand command) {
        if (command.getCity().isBlank() || Objects.isNull(command.getCity()) ||
                command.getStreet().isBlank() || Objects.isNull(command.getStreet()) ||
                command.getHouse().isBlank() || Objects.isNull(command.getHouse())) {
            throw new CreateAddressException("Address not valid");
        }
    }

    public List<Address> getAddress() {
        return addressRepository.findAll();
    }

    public Address getAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(GetAddressByIdException::new);
    }
}