package academy.softserve.os.repository;

import academy.softserve.os.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByCityAndStreetAndHouseAndRoom(String city, String street, String house, String room);
}
