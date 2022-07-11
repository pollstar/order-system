package academy.softserve.os.service.impl;

import academy.softserve.os.exception.LoginIsNotUniqueException;
import academy.softserve.os.model.User;
import academy.softserve.os.model.Worker;
import academy.softserve.os.repository.WorkerRepository;
import academy.softserve.os.service.WorkerService;
import academy.softserve.os.service.command.CreateWorkerCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WorkerServiceImplTest {

    private WorkerService workerService;

    private WorkerRepository workerRepository;


    @BeforeEach
    void init() {
        workerRepository = mock(WorkerRepository.class);
        workerService = new WorkerServiceImpl(workerRepository);
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

        var requestUser = User
                .builder()
                .login("john123")
                .passwordHash("12345")
                .build();

        var requestWorker = Worker
                .builder()
                .firstName("John")
                .lastName("Smith")
                .user(requestUser)
                .build();

        //when
        when(workerRepository.save(requestWorker)).thenAnswer(returnsFirstArg());
        var result = workerService.createWorker(createWorkerCommand);
        //then
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