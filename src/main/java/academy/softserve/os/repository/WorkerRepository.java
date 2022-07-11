package academy.softserve.os.repository;

import academy.softserve.os.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    Worker findByUserLogin(String login);
}
