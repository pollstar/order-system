package academy.softserve.os.repository;

import academy.softserve.os.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByCityAndStreetAndHouseAndRoom(String city, String street, String house, String room);
}
