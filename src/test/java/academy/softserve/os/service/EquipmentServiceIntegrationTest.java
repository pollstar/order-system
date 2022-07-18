package academy.softserve.os.service;

import academy.softserve.os.model.Address;
import academy.softserve.os.model.Client;
import academy.softserve.os.repository.AddressRepository;
import academy.softserve.os.repository.ClientRepository;
import academy.softserve.os.repository.EquipmentRepository;
import academy.softserve.os.service.command.CreateAddressCommand;
import academy.softserve.os.service.command.CreateClientCommand;
import academy.softserve.os.service.command.CreateEquipmentCommand;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class EquipmentServiceIntegrationTest {

    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;

    static Address address;
    static Client client;
    static List<String> descriptionEquipment;

    @BeforeAll
    static void initForAll () {
        address = Address.builder()
                .city("City")
                .street("Street")
                .house("House")
                .build();
        client = Client.builder()
                .name("Client")
                .build();
        descriptionEquipment = List.of("Condition1", "Watercoller", "Condition2");
    }

    @BeforeEach
    void init() {
        equipmentRepository.deleteAll();
        var addressId = addressRepository.save(address).getId();
        var clientId = clientRepository.save(client).getId();

        descriptionEquipment.forEach((description) -> equipmentService.createEquipment(CreateEquipmentCommand.builder()
                .description(description)
                .clientId(clientId)
                .addressId(addressId)
                .build()));
    }

    private static Stream<Arguments> setupTestValues() {
        return Stream.of(
                Arguments.of("con", 2),
                Arguments.of("", 3),
                Arguments.of("name", 0)
        );
    }

    @ParameterizedTest
    @MethodSource("setupTestValues")
    void givenEquipmentDescriptionExample_findEquipmentByDescription_shouldReturnListOfEquipmentWithMatchingDescription(
            String description, Integer sizeList
    ) {
        var equipments = equipmentService.findEquipmentByDescription(description);

        assertEquals(sizeList, equipments.size());
    }

    @Test
    void givenEmptyEquipmentDescription_findEquipmentByDescription_shouldReturnListAllEquipments() {
        var equipments = equipmentService.findEquipmentByDescription("");
        assertEquals(3, equipments.size());
    }

}
