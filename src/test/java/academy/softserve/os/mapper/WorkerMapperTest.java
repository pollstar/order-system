package academy.softserve.os.mapper;

import academy.softserve.os.api.dto.WorkerDTO;
import academy.softserve.os.api.dto.command.CreateWorkerCommandDTO;
import academy.softserve.os.model.User;
import academy.softserve.os.model.Worker;
import academy.softserve.os.service.command.CreateWorkerCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkerMapperTest {

    private final WorkerMapper workerMapper = WorkerMapper.INSTANCE;


    private static Stream<Arguments> provideArguments(){
        var innerUser = User.builder()
                .login("John_123")
                .passwordHash("1234567")
                .build();

        var expectedWorker = Worker
                .builder()
                .firstName("John")
                .lastName("Smith")
                .user(innerUser)
                .build();

        return Stream.of(
                Arguments.of(expectedWorker)
        );
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    void givenWorker_toWorkerDTO_shouldReturnWorkerDTO(Worker worker){
        var expectedWorkerDTO = WorkerDTO
                .builder()
                .login(worker.getUser().getLogin())
                .firstName(worker.getFirstName())
                .lastName(worker.getLastName())
                .build();

        //when
        var workerDTO = workerMapper.toWorkerDTO(worker);

        //then
        assertThat(workerDTO).isEqualTo(expectedWorkerDTO);
    }

    @Test
    void givenCreateWorkerCommandDTO_toCreateWorkerCommand_shouldReturnCreateWorkerCommandDTO(){
        //given
        var expectedCreateWorkerCommand = CreateWorkerCommand
                .builder()
                .login("John_123")
                .firstName("John")
                .lastName("Smith")
                .password("1234567")
                .build();

        var createWorkerCommandDTO = CreateWorkerCommandDTO
                .builder()
                .login("John_123")
                .firstName("John")
                .lastName("Smith")
                .password("1234567")
                .build();

        //when
        var createWorkerCommand = workerMapper.toCreateWorkerCommand(createWorkerCommandDTO);

        //then
        assertThat(createWorkerCommand).isEqualTo(expectedCreateWorkerCommand);
    }
}
