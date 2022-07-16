package academy.softserve.os.mapper;

import academy.softserve.os.api.dto.command.CreateUserCommandDTO;
import academy.softserve.os.service.command.CreateUserCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    CreateUserCommand toCreateUserCommand(CreateUserCommandDTO createUserCommandDTO);
}
