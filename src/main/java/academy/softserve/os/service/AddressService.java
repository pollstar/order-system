package academy.softserve.os.service;

import academy.softserve.os.service.exception.CreateAddressException;
import academy.softserve.os.model.Address;
import academy.softserve.os.repository.AddressRepository;
import academy.softserve.os.service.command.CreateAddressCommand;
import academy.softserve.os.service.exception.GetAddressByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    @Transactional
    public Address createAddress(CreateAddressCommand command) {

        checkToValidCommand(command);

        UnaryOperator<String> removeExtraSpaces = s -> s.toUpperCase().replaceAll("\\s+", " ").trim();
        var address = Address.builder()
                .city(removeExtraSpaces.apply(command.getCity()))
                .street(removeExtraSpaces.apply(command.getStreet()))
                .house(removeExtraSpaces.apply(command.getHouse()))
                .room(removeExtraSpaces.apply(command.getRoom()))
                .build();


        return addressRepository.findByCityAndStreetAndHouseAndRoom(
                address.getCity(),
                address.getStreet(),
                address.getHouse(),
                address.getRoom()
        ).orElseGet(() -> addressRepository.save(address));
    }

    private void checkToValidCommand(CreateAddressCommand command) {
        if (Objects.isNull(command.getCity()) || command.getCity().isBlank() ||
                Objects.isNull(command.getStreet()) || command.getStreet().isBlank() ||
                Objects.isNull(command.getHouse()) || command.getHouse().isBlank()) {
            throw new CreateAddressException();
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