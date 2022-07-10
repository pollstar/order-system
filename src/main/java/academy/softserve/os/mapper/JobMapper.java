package academy.softserve.os.mapper;


import academy.softserve.os.api.dto.JobDTO;
import academy.softserve.os.api.dto.command.CreateJobCommandDTO;
import academy.softserve.os.model.Job;
import academy.softserve.os.service.command.CreateJobCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobMapper {
    CreateJobCommand toModel(CreateJobCommandDTO dto);

    JobDTO toDto(Job job);
}
