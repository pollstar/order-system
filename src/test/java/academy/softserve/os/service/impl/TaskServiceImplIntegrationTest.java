package academy.softserve.os.service.impl;

import academy.softserve.os.model.Job;
import academy.softserve.os.repository.JobRepository;
import academy.softserve.os.repository.TaskRepository;
import academy.softserve.os.service.ClientService;
import academy.softserve.os.service.OrderService;
import academy.softserve.os.service.TaskService;
import academy.softserve.os.service.WorkerService;
import academy.softserve.os.service.command.CreateClientCommand;
import academy.softserve.os.service.command.CreateOrderCommand;
import academy.softserve.os.service.command.CreateTaskCommand;
import academy.softserve.os.service.command.CreateWorkerCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class TaskServiceImplIntegrationTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private ClientService clientService;

    @Test
    void givenCreateTaskCommand_createTask_shouldReturnTask() {
        //given
        var job = jobRepository.save(Job
                .builder()
                .description("Job description")
                .build());
        var client = clientService.createClient(new CreateClientCommand("Anna"));
        var order = orderService.createOrder(CreateOrderCommand.builder()
                .clientId(client.getId())
                .closingDate(LocalDateTime.MAX)
                .phase(12)
                .placementDate(LocalDateTime.now())
                .description("Order description")
                .build());
        var worker = workerService.createWorker(CreateWorkerCommand.builder()
                .firstName("John")
                .lastName("Smith")
                .login("John123")
                .password("12345678")
                .build());
        var createTaskCommand = CreateTaskCommand.builder()
                .jobId(job.getId())
                .orderId(order.getId())
                .workerId(worker.getId())
                .createWorkerId(worker.getUser().getId())
                .comment("Some comments for task")
                .partFactor(0.7)
                .build();

        //when
        var result = taskService.createTask(createTaskCommand);

        //then
        assertThat(taskRepository.count()).isEqualTo(1);

        assertThat(result.getPartFactor()).isEqualTo(createTaskCommand.getPartFactor());
        assertThat(result.getComment()).isEqualTo(createTaskCommand.getComment());

        assertThat(result.getCreator().getFirstName()).isEqualTo(worker.getFirstName());
        assertThat(result.getCreator().getLastName()).isEqualTo(worker.getLastName());

        assertThat(result.getWorker().getFirstName()).isEqualTo(worker.getFirstName());
        assertThat(result.getWorker().getLastName()).isEqualTo(worker.getLastName());

        assertThat(result.getJob().getDescription()).isEqualTo(job.getDescription());
        assertThat(result.getOrder().getDescription()).isEqualTo(order.getDescription());

        assertThat(result.getOrder().getPhase()).isEqualTo(order.getPhase());
        assertThat(result.getOrder().getPlacementDate()).isEqualTo(order.getPlacementDate());
        assertThat(result.getOrder().getClosingDate()).isEqualTo(order.getClosingDate());
    }
}