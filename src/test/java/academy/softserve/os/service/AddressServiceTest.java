package academy.softserve.os.service;

import academy.softserve.os.service.exception.CreateAddressException;
import academy.softserve.os.model.Address;
import academy.softserve.os.repository.AddressRepository;
import academy.softserve.os.service.command.CreateAddressCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

class AddressServiceTest {
    private AddressRepository repository;
    private AddressService service;
    private CreateAddressCommand command;
    private Address address;

    @BeforeEach
    void setUp() {
        repository = mock(AddressRepository.class);
        service = new AddressService(repository);
        address = mock(Address.class);

        command = CreateAddressCommand.builder()
                .city(" Харків")
                .street("Сумска  вул.")
                .house("буд.23 ")
                .room("вітальня")
                .build();
    }

    @Test
    void givenValidCreateAddressCommand_createAddress_shouldReturnCreatedAddress() {
        //given
        var addressOut = Address.builder()
                .id(1L)
                .city("ХАРКІВ")
                .street("СУМСКА ВУЛ.")
                .house("БУД.23")
                .room("ВІТАЛЬНЯ")
                .build();
        //when
        when(repository.save(any(Address.class))).then(AdditionalAnswers.returnsFirstArg());
        when(repository.findByCityAndStreetAndHouseAndRoom(
                address.getCity(),
                address.getStreet(),
                address.getHouse(),
                address.getRoom()
        )).thenReturn(Optional.empty());

        var address1 = service.createAddress(command);
        //then
        assertEquals(address1.getCity(), addressOut.getCity());
        assertEquals(address1.getStreet(), addressOut.getStreet());
        assertEquals(address1.getHouse(), addressOut.getHouse());
        assertEquals(address1.getRoom(), addressOut.getRoom());
    }

    @Test
    void givenValidCreateAddressCommandWithExistingAddresss_createAddress_shouldReturnExistingAddress() {
        //given
        var address1 = Address.builder()
                .id(1L)
                .city("ХАРКІВ")
                .street("СУМСКА ВУЛ.")
                .house("БУД.23")
                .room("ВІТАЛЬНЯ")
                .build();

        //when
        when(repository.save(any(Address.class))).thenReturn(address1);
        when(repository.findByCityAndStreetAndHouseAndRoom(
                address1.getCity(),
                address1.getStreet(),
                address1.getHouse(),
                address1.getRoom()
        )).thenReturn(Optional.of(address1));

        var addressOut = service.createAddress(command);
        //then
        assertEquals(address1.getId(), addressOut.getId());
        assertEquals(address1.getCity(), addressOut.getCity());
        assertEquals(address1.getStreet(), addressOut.getStreet());
        assertEquals(address1.getHouse(), addressOut.getHouse());
        assertEquals(address1.getRoom(), addressOut.getRoom());
    }

    @Test
    void givenNotValidCreateAddressCommandWithEmptyFields_createAddress_shouldThrowException() {
        //given
        var command1 = CreateAddressCommand.builder()
                .city("")
                .house("Сумска  вул.")
                .street("буд.23 ")
                .room("вітальня")
                .build();

        var command2 = CreateAddressCommand.builder()
                .city(" Харків")
                .house("")
                .street("буд.23 ")
                .room("вітальня")
                .build();

        var command3 = CreateAddressCommand.builder()
                .city(" Харків")
                .house("Сумска  вул.")
                .street("")
                .room("вітальня")
                .build();

        //then
        assertThrows(CreateAddressException.class, () -> service.createAddress(command1));
        assertThrows(CreateAddressException.class, () -> service.createAddress(command2));
        assertThrows(CreateAddressException.class, () -> service.createAddress(command3));
    }

    @Test
    void givenCreateAddressCommandWithEmptyFieldRoom_createAddress_shouldReturnCreatedAddress() {
        //given
        command.setRoom("");

        var addressOut = Address.builder()
                .id(1L)
                .city("ХАРКІВ")
                .street("СУМСКА ВУЛ.")
                .house("БУД.23")
                .room("")
                .build();
        //when
        when(repository.save(any(Address.class))).thenReturn(addressOut);
        when(repository.findByCityAndStreetAndHouseAndRoom(
                address.getCity(),
                address.getStreet(),
                address.getHouse(),
                address.getRoom()
        )).thenReturn(Optional.empty());

        var address1 = service.createAddress(command);
        //then
        assertEquals(address1.getId(), addressOut.getId());
        assertEquals(address1.getCity(), addressOut.getCity());
        assertEquals(address1.getStreet(), addressOut.getStreet());
        assertEquals(address1.getHouse(), addressOut.getHouse());
        assertEquals(address1.getRoom(), addressOut.getRoom());
    }
}