package academy.softserve.os.repository;

import academy.softserve.os.model.ERole;
import academy.softserve.os.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}
