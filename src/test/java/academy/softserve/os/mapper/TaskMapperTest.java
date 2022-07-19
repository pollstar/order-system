package academy.softserve.os.mapper;

import academy.softserve.os.api.dto.TaskDTO;
import academy.softserve.os.api.dto.command.CreateTaskCommandDTO;
import academy.softserve.os.model.Job;
import academy.softserve.os.model.Order;
import academy.softserve.os.model.Task;
import academy.softserve.os.model.Worker;
import academy.softserve.os.service.command.CreateTaskCommand;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskMapperTest {
    private final TaskMapper mapper = Mappers.getMapper(TaskMapper.class);

    @Test
    void givenCreateTaskCommandDTO_toCreateTaskCommand_shouldReturnCreateTaskCommand() {
        //given
        var createTaskCommandDTO = CreateTaskCommandDTO
                .builder()
                .comment("Comment")
                .jobId(1L)
                .orderId(1L)
                .workerId(15L)
                .partFactor(0.5)
                .build();

        //when
        var result = mapper.toCreateTaskCommand(createTaskCommandDTO);

        //then
        var createTaskCommand = CreateTaskCommand.builder()
                .comment("Comment")
                .jobId(1L)
                .orderId(1L)
                .workerId(15L)
                .partFactor(0.5)
                .build();
        assertThat(result.getOrderId()).isEqualTo(createTaskCommand.getOrderId());
        assertThat(result.getJobId()).isEqualTo(createTaskCommand.getJobId());
        assertThat(result.getWorkerId()).isEqualTo(createTaskCommand.getWorkerId());
        assertThat(result.getPartFactor()).isEqualTo(createTaskCommand.getPartFactor());
        assertThat(result.getComment()).isEqualTo(createTaskCommand.getComment());
    }

    @Test
    void givenTask_toTaskDTO_shouldReturnTaskDTO() {
        //given
        var order = new Order();
        order.setId(3L);
        var creator = new Worker();
        creator.setId(5L);
        var job = new Job();
        job.setId(1L);
        var worker = new Worker();
        worker.setId(10L);

        var task = Task.builder()
                .comment("Comment")
                .id(1L)
                .partFactor(0.5)
                .creator(creator)
                .order(order)
                .worker(worker)
                .job(job)
                .build();

        //when
        var result = mapper.toTaskDTO(task);

        //then
        var taskDTO = TaskDTO
                .builder()
                .comment("Comment")
                .jobId(1L)
                .orderId(3L)
                .creatorId(5L)
                .partFactor(0.5)
                .workerId(10L)
                .build();
        assertThat(result.getComment()).isEqualTo(taskDTO.getComment());
        assertThat(result.getJobId()).isEqualTo(taskDTO.getJobId());
        assertThat(result.getPartFactor()).isEqualTo(taskDTO.getPartFactor());
        assertThat(result.getCreatorId()).isEqualTo(taskDTO.getCreatorId());
        assertThat(result.getWorkerId()).isEqualTo(taskDTO.getWorkerId());
    }
}
