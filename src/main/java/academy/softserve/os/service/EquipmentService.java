package academy.softserve.os.service;

import academy.softserve.os.model.Equipment;
import academy.softserve.os.repository.AddressRepository;
import academy.softserve.os.repository.ClientRepository;
import academy.softserve.os.repository.EquipmentRepository;
import academy.softserve.os.service.command.CreateEquipmentCommand;
import academy.softserve.os.exception.CreateEquipmentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;
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
}