package academy.softserve.os.repository;

import academy.softserve.os.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    Optional<Worker> findByUserLogin(String login);

    List<Worker> findWorkersByFirstNameIgnoreCaseOrLastNameIgnoreCase(String firstName, String lastName);

    List<Worker> findWorkersByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);

    Optional<Worker> findByUserId(Long id);
}
