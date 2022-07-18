package academy.softserve.os.service.impl;

import academy.softserve.os.model.Role;
import academy.softserve.os.model.RoleAssignment;
import academy.softserve.os.model.User;
import academy.softserve.os.model.Worker;
import academy.softserve.os.repository.WorkerRepository;
import academy.softserve.os.service.UserService;
import academy.softserve.os.service.WorkerService;
import academy.softserve.os.service.command.CreateUserCommand;
import academy.softserve.os.service.command.CreateWorkerCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WorkerServiceImplTest {

    private WorkerService workerService;
    private WorkerRepository workerRepository;
    private UserService userService;

    @BeforeEach
    void init() {
        workerRepository = mock(WorkerRepository.class);
        userService = mock(UserService.class);
        workerService = new WorkerServiceImpl(workerRepository, userService);
    }

    @Test
    void givenWorkerService_createWorker_shouldReturnCreatedWorker() {


        var createWorkerCommand = CreateWorkerCommand
                .builder()
                .firstName("John")
                .lastName("Smith")
                .login("john123")
                .password("12345")
                .build();

        var user = User
                .builder()
                .login("john123")
                .passwordHash("12345")
                .roles(List.of(new RoleAssignment(Role.ROLE_WORKER)))
                .build();


        when(workerRepository.save(any(Worker.class))).thenAnswer(returnsFirstArg());
        when(userService.createUser(any(CreateUserCommand.class))).thenReturn(user);
        var result = workerService.createWorker(createWorkerCommand);


        var requestUser = User
                .builder()
                .login("john123")
                .passwordHash("12345")
                .roles(List.of(new RoleAssignment(Role.ROLE_WORKER)))
                .build();

        var requestWorker = Worker
                .builder()
                .firstName("John")
                .lastName("Smith")
                .user(requestUser)
                .build();

        assertThat(result.getFirstName()).isEqualTo(requestWorker.getFirstName());
        assertThat(result.getLastName()).isEqualTo(requestWorker.getLastName());
        assertThat(result.getUser().getLogin()).isEqualTo(requestWorker.getUser().getLogin());
        assertThat(result.getUser().getPasswordHash()).isEqualTo(requestWorker.getUser().getPasswordHash());
    }
}