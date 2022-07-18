package academy.softserve.os.repository;

import academy.softserve.os.model.Address;
import academy.softserve.os.model.Client;
import academy.softserve.os.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    Optional<Equipment> findByDescriptionAndClientAndAddress(String description, Client client, Address address);
}
