package academy.softserve.os;

import academy.softserve.os.model.Address;
import academy.softserve.os.model.Client;
import academy.softserve.os.repository.AddressRepository;
import academy.softserve.os.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataBaseLoader implements CommandLineRunner {
    private final AddressRepository addressRepository;
    private final ClientRepository clientRepository;

    @Override
    public void run(String... args) {
        tableAddressLoader();
        tableClientLoader();
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
