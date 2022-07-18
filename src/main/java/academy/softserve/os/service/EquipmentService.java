package academy.softserve.os.service;

import academy.softserve.os.model.Equipment;
import academy.softserve.os.repository.AddressRepository;
import academy.softserve.os.repository.ClientRepository;
import academy.softserve.os.repository.EquipmentRepository;
import academy.softserve.os.service.command.CreateEquipmentCommand;
import academy.softserve.os.exception.CreateEquipmentException;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
    public Equipment createEquipment(CreateEquipmentCommand command) {
        checkToValidEquipment(command);

        var client = clientRepository.findById(command.getClientId())
                .orElseThrow(() -> new CreateEquipmentException("Client ID not found."));
        var address = addressRepository.findById(command.getAddressId())
                .orElseThrow(() -> new CreateEquipmentException("Address ID not found."));

        UnaryOperator<String> removingExtraSpaces = s -> s.replaceAll("\\s+", " ").trim();
        var equipment = Equipment.builder()
                .description(removingExtraSpaces.apply(command.getDescription()))
                .client(client)
                .address(address)
                .build();

        return equipmentRepository.findByDescriptionAndClientAndAddress(
                equipment.getDescription(),
                equipment.getClient(),
                equipment.getAddress()
        ).orElseGet(() -> equipmentRepository.save(equipment));
    }

    private void checkToValidEquipment(CreateEquipmentCommand command) {
        if (Objects.isNull(command.getDescription()) || command.getDescription().trim().isBlank()) {
            throw new CreateEquipmentException("Description not present.");
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
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Equipment> example = Example.of(equipment, caseInsensitiveExampleMatcher);

        return equipmentRepository.findAll(example);
    }
}