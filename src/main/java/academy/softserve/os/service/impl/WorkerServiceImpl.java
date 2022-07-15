package academy.softserve.os.service.impl;

import academy.softserve.os.exception.LoginIsNotUniqueException;
import academy.softserve.os.model.Role;
import academy.softserve.os.model.RoleAssignment;
import academy.softserve.os.model.User;
import academy.softserve.os.model.Worker;
import academy.softserve.os.repository.WorkerRepository;
import academy.softserve.os.service.WorkerService;
import academy.softserve.os.service.command.CreateWorkerCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;

    @Autowired
    WorkerServiceImpl(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Override
    @Transactional
    public Worker createWorker(CreateWorkerCommand createWorkerCommand) {
        if (!loginIsUnique(createWorkerCommand.getLogin())) {
            throw new LoginIsNotUniqueException();
        }

        var user = getUserFromCommand(createWorkerCommand);
        var worker = getWorkerFromCommand(createWorkerCommand);
        worker.setUser(user);
        return workerRepository.save(worker);
    }

    private User getUserFromCommand(CreateWorkerCommand createWorkerCommand) {
        var roleAssignment = new RoleAssignment(Role.ROLE_WORKER);
        return User.builder()
                .login(createWorkerCommand.getLogin())
                .passwordHash(createWorkerCommand.getPassword())
                .roles(Set.of(roleAssignment))
                .build();
    }

    private boolean loginIsUnique(String login) {
        var worker = workerRepository.findByUserLogin(login);
        return worker.isEmpty();
    }

    private Worker getWorkerFromCommand(CreateWorkerCommand command) {
        return Worker.builder()
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .build();
    }
}
