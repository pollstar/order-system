package academy.softserve.os.mapper;

import academy.softserve.os.api.dto.TaskDTO;
import academy.softserve.os.api.dto.command.CreateTaskCommandDTO;
import academy.softserve.os.model.Task;
import academy.softserve.os.service.command.CreateTaskCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    CreateTaskCommand toCreateTaskCommand(CreateTaskCommandDTO dto);

    @Mapping(target = "orderId", source = "task.order.id")
    @Mapping(target = "workerId", source = "task.worker.id")
    @Mapping(target = "creatorId", source = "task.creator.id")
    @Mapping(target = "jobId", source = "task.job.id")
    TaskDTO toTaskDTO(Task task);
}
