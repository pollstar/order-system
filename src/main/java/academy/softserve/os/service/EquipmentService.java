package academy.softserve.os.service;

import academy.softserve.os.api.dto.EquipmentDTO;
import academy.softserve.os.model.Address;
import academy.softserve.os.model.Client;
import academy.softserve.os.model.Equipment;
import academy.softserve.os.repository.AddressRepository;
import academy.softserve.os.repository.ClientRepository;
import academy.softserve.os.repository.EquipmentRepository;
import academy.softserve.os.service.command.CreateEquipmentCommand;
import academy.softserve.os.exception.CreateEquipmentException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public Optional<Equipment> createEquipment(CreateEquipmentCommand command) {
        Address address = null;
        Client client = null;

        checkToValidEquipment(command);

        if (command.getClientId() != null) {
            client = clientRepository.findById(command.getClientId())
                    .orElseThrow(() -> new CreateEquipmentException("Client ID not found."));
        }
        if (command.getAddressId() != null) {
            address = addressRepository.findById(command.getAddressId())
                    .orElseThrow(() -> new CreateEquipmentException("Address ID not found."));
        }
        UnaryOperator<String> removingExtraSpaces = s -> s.replaceAll("\\s+", " ").trim();
        var equipment = Equipment.builder()
                .description(removingExtraSpaces.apply(command.getDescription()))
                .client(client)
                .address(address)
                .build();

        return Optional.of(equipmentRepository.findByDescriptionAndClientAndAddress(
                equipment.getDescription(),
                equipment.getClient(),
                equipment.getAddress()
        ).orElseGet(() -> equipmentRepository.save(equipment)));
    }

    private void checkToValidEquipment(CreateEquipmentCommand command) {
        if (command.getDescription().isBlank() || Objects.isNull(command.getDescription())) {
            throw new CreateEquipmentException();
        }
    }

    public Optional<Equipment> getEquipmentById(Long id) {
        return equipmentRepository.findById(id);
    }

    public List<Equipment> findEquipment(String description) {
        if (description == null || description.isEmpty()) {
            return equipmentRepository.findAll();
        }
        Equipment equipment = new Equipment();
        equipment.setDescription(description);
        ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAny().withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);//.withIgnoreCase();
        Example<Equipment> example = Example.of(equipment, caseInsensitiveExampleMatcher);

        var res = equipmentRepository.findAll(example);
        return res;
    }
}