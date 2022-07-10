package academy.softserve.os.mapper;

import academy.softserve.os.model.User;
import academy.softserve.os.service.command.CreateWorkerCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "passwordHash", source = "password")
    User toUser(CreateWorkerCommand command);
}
