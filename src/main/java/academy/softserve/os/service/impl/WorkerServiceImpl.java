package academy.softserve.os.service.impl;

import academy.softserve.os.model.Role;
import academy.softserve.os.model.Worker;
import academy.softserve.os.repository.WorkerRepository;
import academy.softserve.os.service.UserService;
import academy.softserve.os.service.WorkerService;
import academy.softserve.os.service.command.CreateUserCommand;
import academy.softserve.os.service.command.CreateWorkerCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;
    private final UserService userService;

    @Autowired
    WorkerServiceImpl(WorkerRepository workerRepository,
                      UserService userService) {
        this.workerRepository = workerRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Worker createWorker(CreateWorkerCommand createWorkerCommand) {

        var user = userService.createUser(getCreateUserCommand(createWorkerCommand));
        var worker = getWorkerFromCommand(createWorkerCommand);
        worker.setUser(user);
        return workerRepository.save(worker);
    }

    @Override
    public List<Worker> findWorkersByName(String name) {
        if (name == null){
            return workerRepository.findAll();
        }
        var nameArgumentsList = name.split(" ");
        if (nameArgumentsList.length == 1){
            return workerRepository.findWorkersByFirstNameIgnoreCaseOrLastNameIgnoreCase(nameArgumentsList[0], nameArgumentsList[0]);
        }else {
            return workerRepository.findWorkersByFirstNameIgnoreCaseAndLastNameIgnoreCase(nameArgumentsList[0], nameArgumentsList[1]);
        }

    }

    @Override
    public List<Worker> findAllWorkers() {
        return workerRepository.findAll();
    }

    @Override
    public List<Worker> findAllWorkersByName(String firstName, String lastName) {
        return workerRepository.findWorkersByFirstNameIgnoreCaseOrLastNameIgnoreCase(firstName, lastName);
    }

    @Override
    public List<Worker> findAllWorkersByFirstNameAndLastName(String firstName, String lastName) {
        return workerRepository.findWorkersByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName);
    }

    private CreateUserCommand getCreateUserCommand(CreateWorkerCommand createWorkerCommand) {
        return CreateUserCommand
                .builder()
                .login(createWorkerCommand.getLogin())
                .password(createWorkerCommand.getPassword())
                .roles(Set.of(Role.ROLE_WORKER.name()))
                .build();
    }

    private Worker getWorkerFromCommand(CreateWorkerCommand command) {
        return Worker.builder()
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .build();
    }
}
