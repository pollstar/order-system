package academy.softserve.os.mapper;

import academy.softserve.os.api.dto.WorkerDTO;
import academy.softserve.os.api.dto.command.CreateWorkerCommandDTO;
import academy.softserve.os.model.Worker;
import academy.softserve.os.service.command.CreateWorkerCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WorkerMapper {

    WorkerMapper INSTANCE = Mappers.getMapper(WorkerMapper.class);

    @Mapping(target = "user.login", source = "login")
    @Mapping(target = "user.passwordHash", source = "password")
    Worker toWorker(CreateWorkerCommand createWorkerCommand);

    @Mapping(target = "login", source = "user.login")
    WorkerDTO toWorkerDTO(Worker worker);

    CreateWorkerCommand toCreateWorkerCommand(CreateWorkerCommandDTO dto);
}
