package academy.softserve.os.mapper;

import academy.softserve.os.model.User;
import academy.softserve.os.service.command.CreateWorkerCommand;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Test
    void givenCreateWorkerCommand_toUser_shouldReturnUser() {
        //given
        var createWorkerCommand = CreateWorkerCommand
                .builder()
                .login("John_123")
                .firstName("John")
                .lastName("Smith")
                .password("1234567")
                .build();

        var expectedUser = User
                .builder()
                .login("John_123")
                .passwordHash("1234567")
                .build();

        //when
        var user = userMapper.toUser(createWorkerCommand);

        //then
        assertThat(user).isEqualTo(expectedUser);

    }
}
