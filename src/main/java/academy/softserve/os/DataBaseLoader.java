package academy.softserve.os;

import academy.softserve.os.mapper.EquipmentMapper;
import academy.softserve.os.model.Address;
import academy.softserve.os.model.Client;
import academy.softserve.os.model.Equipment;
import academy.softserve.os.repository.AddressRepository;
import academy.softserve.os.repository.ClientRepository;
import academy.softserve.os.repository.EquipmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataBaseLoader implements CommandLineRunner {
    private final AddressRepository addressRepository;
    private final ClientRepository clientRepository;
    private final EquipmentRepository equipmentRepository;

    @Override
    public void run(String... args) {
        tableAddressLoader();
        tableClientLoader();
        tableEquipmentLoader();
    }

    private void tableEquipmentLoader() {
        equipmentRepository.save(
                Equipment.builder()
                        .description("Кондиционер 1")
                        .client(clientRepository.findById(1L).get())
                        .address(addressRepository.findById(4L).get())
                        .build()
        );
        equipmentRepository.save(
                Equipment.builder()
                        .description("Кондиционер 2")
                        .client(clientRepository.findById(1L).get())
                        .address(addressRepository.findById(2L).get())
                        .build()
        );
        equipmentRepository.save(
                Equipment.builder()
                        .description("Охолоджувач")
                        .client(clientRepository.findById(2L).get())
                        .address(addressRepository.findById(3L).get())
                        .build()
        );
        equipmentRepository.save(
                Equipment.builder()
                        .description("Осушувач")
                        .client(clientRepository.findById(3L).get())
                        .address(addressRepository.findById(1L).get())
                        .build()
        );
    }

    private void tableClientLoader() {
        clientRepository.save(
                Client.builder()
                        .id(1L)
                        .name("Обленерго")
                        .build()
        );
        clientRepository.save(
                Client.builder()
                        .id(2L)
                        .name("Цукеркова фабрика \"Харківчанка\"")
                        .build()
        );
        clientRepository.save(
                Client.builder()
                        .id(3L)
                        .name("Ресторан \"Пузата хата\"")
                        .build()
        );
    }

    private void tableAddressLoader() {
        addressRepository.save(
                Address.builder()
                    .id(1L)
                    .city("ХАРКІВ")
                    .street("СУМСЬКА ВУЛ.")
                    .house("2")
                    .room("23")
                    .build());
        addressRepository.save(
                Address.builder()
                        .id(2L)
                        .city("ХАРКІВ")
                        .street("ЛОЗІВСЬКА ВУЛ.")
                        .house("23/26")
                        .room("ШОКОЛАДНИЙ ЦЕХ")
                        .build());
        addressRepository.save(
                Address.builder()
                        .id(3L)
                        .city("ХАРКІВ")
                        .street("ЛОЗІВСЬКА ВУЛ.")
                        .house("23/26")
                        .room("КАРАМЕЛЬНИЙ ЦЕХ")
                        .build());
        addressRepository.save(
                Address.builder()
                        .id(4L)
                        .city("ХАРКІВ")
                        .street("ПЛЕХАНІВСЬКА ВУЛ.")
                        .house("34")
                        .room("КІМ.205")
                        .build());
    }
}
