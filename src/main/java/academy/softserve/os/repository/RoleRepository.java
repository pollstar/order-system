package academy.softserve.os.repository;

import academy.softserve.os.model.Role;
import academy.softserve.os.model.RoleAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleAssignment, Long> {

    Optional<RoleAssignment> findByName(Role name);
}
