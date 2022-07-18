package academy.softserve.os.service;

import academy.softserve.os.exception.CreateEquipmentException;
import academy.softserve.os.model.Address;
import academy.softserve.os.model.Client;
import academy.softserve.os.model.Equipment;
import academy.softserve.os.repository.AddressRepository;
import academy.softserve.os.repository.ClientRepository;
import academy.softserve.os.repository.EquipmentRepository;
import academy.softserve.os.service.command.CreateAddressCommand;
import academy.softserve.os.service.command.CreateEquipmentCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class EquipmentServiceTest {
    private EquipmentRepository equipmentRepository;
    private ClientRepository clientRepository;
    private AddressRepository addressRepository;
    private AddressService addressService;
    private EquipmentService equipmentService;
    private CreateAddressCommand addressCommand;

    private Address address;
    private Equipment equipment;
    private CreateEquipmentCommand command;
    private Client client;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        addressRepository = mock(AddressRepository.class);
        equipmentRepository = mock(EquipmentRepository.class);
        equipmentService = new EquipmentService(equipmentRepository, clientRepository, addressRepository);
        address = Address.builder()
                .id(1L)
                .city("ХАРКІВ")
                .street("СУМСКА ВУЛ.")
                .house("БУД.23")
                .room("ВІТАЛЬНЯ")
                .build();
        client = Client.builder()
                .id(2L)
                .name("Client")
                .build();
        command = CreateEquipmentCommand.builder()
                .description("Description")
                .addressId(1L)
                .clientId(2L)
                .build();
        equipment = Equipment.builder()
                .id(1L)
                .description("Equipment")
                .client(client)
                .address(address)
                .build();
    }

    @Test
    void givenValidCreateEquipmentCommand_createEquipment_shouldReturnCreatedEquipment() {


        when(addressRepository.findById(any(Long.class))).thenReturn(Optional.of(address));
        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(client));
        when(equipmentRepository.save(any(Equipment.class))).thenReturn(equipment);
        when(equipmentRepository.findByDescriptionAndClientAndAddress(
                equipment.getDescription(),
                equipment.getClient(),
                equipment.getAddress()
        )).thenReturn(Optional.empty());

        var equipment1 = equipmentService.createEquipment(command);

        assertEquals(equipment1.getDescription() , equipment.getDescription());
    }

    @Test
    void givenCreateEquipmentCommandWithNotAddressField_createEquipment_shouldThrowException() {


        when(addressRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(client));
        when(equipmentRepository.save(any(Equipment.class))).thenReturn(equipment);
        when(equipmentRepository.findByDescriptionAndClientAndAddress(
                equipment.getDescription(),
                equipment.getClient(),
                equipment.getAddress()
        )).thenReturn(Optional.empty());

        var thrown = Assertions.assertThrows(CreateEquipmentException.class,
                () -> equipmentService.createEquipment(command));
        assertEquals("Create equipment error. Address ID not found.", thrown.getMessage());
    }

    @Test
    void givenCreateEquipmentCommandWithNullAddressField_createEquipment_shouldThrowException() {

        String errorMessage = "Error message";

        when(addressRepository.findById(any(Long.class))).thenThrow(new InvalidDataAccessApiUsageException(errorMessage));
        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(client));
        when(equipmentRepository.save(any(Equipment.class))).thenReturn(equipment);
        when(equipmentRepository.findByDescriptionAndClientAndAddress(
                equipment.getDescription(),
                equipment.getClient(),
                equipment.getAddress()
        )).thenReturn(Optional.empty());

        var thrown = Assertions.assertThrows(InvalidDataAccessApiUsageException.class,
                () -> equipmentService.createEquipment(command));
        assertEquals(errorMessage, thrown.getMessage());
    }

    @Test
    void givenCreateEquipmentCommandWithNotClientField_createEquipment_shouldThrowException() {


        when(addressRepository.findById(any(Long.class))).thenReturn(Optional.of(address));
        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        when(equipmentRepository.save(any(Equipment.class))).thenReturn(equipment);
        when(equipmentRepository.findByDescriptionAndClientAndAddress(
                equipment.getDescription(),
                equipment.getClient(),
                equipment.getAddress()
        )).thenReturn(Optional.empty());

        var thrown = Assertions.assertThrows(CreateEquipmentException.class,
                () -> equipmentService.createEquipment(command));
        assertEquals("Create equipment error. Client ID not found.", thrown.getMessage());
    }

    @Test
    void givenCreateEquipmentCommandWithNullClientField_createEquipment_shouldThrowException() {

        String errorMessage = "Create equipment error. Client ID error.";
        when(addressRepository.findById(any(Long.class))).thenReturn(Optional.of(address));
        when(clientRepository.findById(any(Long.class))).thenThrow(new InvalidDataAccessApiUsageException(errorMessage));
        when(equipmentRepository.save(any(Equipment.class))).thenReturn(equipment);
        when(equipmentRepository.findByDescriptionAndClientAndAddress(
                equipment.getDescription(),
                equipment.getClient(),
                equipment.getAddress()
        )).thenReturn(Optional.empty());

        var thrown = Assertions.assertThrows(InvalidDataAccessApiUsageException.class,
                () -> equipmentService.createEquipment(command));
        assertEquals(errorMessage, thrown.getMessage());
    }

    @Test
    void givenCreateEquipmentCommandWithNullDescriptionField_createEquipment_shouldThrowException() {

        command.setDescription(null);

        when(addressRepository.findById(any(Long.class))).thenReturn(Optional.of(address));
        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(client));
        when(equipmentRepository.save(any(Equipment.class))).thenReturn(equipment);
        when(equipmentRepository.findByDescriptionAndClientAndAddress(
                equipment.getDescription(),
                equipment.getClient(),
                equipment.getAddress()
        )).thenReturn(Optional.empty());

        var thrown = Assertions.assertThrows(CreateEquipmentException.class,
                () -> equipmentService.createEquipment(command));
        assertEquals("Create equipment error. Description not present.", thrown.getMessage());
    }

    @Test
    void givenCreateEquipmentCommandWithEmptyDescriptionField_createEquipment_shouldThrowException() {

        command.setDescription(" ");

        when(addressRepository.findById(any(Long.class))).thenReturn(Optional.of(address));
        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(client));
        when(equipmentRepository.save(any(Equipment.class))).thenReturn(equipment);
        when(equipmentRepository.findByDescriptionAndClientAndAddress(
                equipment.getDescription(),
                equipment.getClient(),
                equipment.getAddress()
        )).thenReturn(Optional.empty());

        var thrown = Assertions.assertThrows(CreateEquipmentException.class,
                () -> equipmentService.createEquipment(command));
        assertEquals("Create equipment error. Description not present.", thrown.getMessage());
    }
}