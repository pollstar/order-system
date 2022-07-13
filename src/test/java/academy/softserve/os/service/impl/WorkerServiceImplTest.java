package academy.softserve.os.service.impl;

import academy.softserve.os.exception.LoginIsNotUniqueException;
import academy.softserve.os.model.ERole;
import academy.softserve.os.model.Role;
import academy.softserve.os.model.User;
import academy.softserve.os.model.Worker;
import academy.softserve.os.repository.RoleRepository;
import academy.softserve.os.repository.UserRepository;
import academy.softserve.os.repository.WorkerRepository;
import academy.softserve.os.service.WorkerService;
import academy.softserve.os.service.command.CreateWorkerCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WorkerServiceImplTest {

    private WorkerService workerService;

    private WorkerRepository workerRepository;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    @BeforeEach
    void init() {
        workerRepository = mock(WorkerRepository.class);
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        workerService = new WorkerServiceImpl(workerRepository, userRepository, roleRepository);
    }

    @Test
    void givenWorkerService_createWorker_shouldReturnCreatedWorker() {

        //given
        var createWorkerCommand = CreateWorkerCommand
                .builder()
                .firstName("John")
                .lastName("Smith")
                .login("john123")
                .password("12345")
                .build();


        //when
        when(workerRepository.save(any(Worker.class))).thenAnswer(returnsFirstArg());
        when(userRepository.save(any(User.class))).thenAnswer(returnsFirstArg());
        when(roleRepository.findByName(any(ERole.class))).thenReturn(Optional.of(new Role()));
        var result = workerService.createWorker(createWorkerCommand);

        //then
        var requestUser = User
                .builder()
                .login("john123")
                .passwordHash("12345")
                .roles(Set.of(new Role()))
                .build();

        var requestWorker = Worker
                .builder()
                .firstName("John")
                .lastName("Smith")
                .user(requestUser)
                .build();
        assertThat(result).isEqualTo(requestWorker);
    }

    @Test
    void givenWorkerService_createWorker_shouldThrowLoginIsNotUniqueException() {

        //when
        when(workerRepository.findByUserLogin(any())).thenReturn(Optional.of(new Worker()));
        //then
        assertThatThrownBy(() -> workerService.createWorker(new CreateWorkerCommand()))
                .isInstanceOf(LoginIsNotUniqueException.class);
    }

}