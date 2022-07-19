package academy.softserve.os.service.impl;

import academy.softserve.os.exception.CreateTaskException;
import academy.softserve.os.model.Job;
import academy.softserve.os.model.Order;
import academy.softserve.os.model.Task;
import academy.softserve.os.model.Worker;
import academy.softserve.os.repository.JobRepository;
import academy.softserve.os.repository.OrderRepository;
import academy.softserve.os.repository.TaskRepository;
import academy.softserve.os.repository.WorkerRepository;
import academy.softserve.os.service.TaskService;
import academy.softserve.os.service.command.CreateTaskCommand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskServiceImplTest {
    private final TaskRepository taskRepository;
    private final OrderRepository orderRepository;
    private final WorkerRepository workerRepository;
    private final JobRepository jobRepository;

    private final TaskService taskService;

    public TaskServiceImplTest() {
        this.taskRepository = mock(TaskRepository.class);
        this.orderRepository = mock(OrderRepository.class);
        this.workerRepository = mock(WorkerRepository.class);
        this.jobRepository = mock(JobRepository.class);
        this.taskService = new TaskServiceImpl(taskRepository, orderRepository, workerRepository, jobRepository);
    }

    @Test
    void givenCreateTaskCommand_createTask_shouldReturnTask() {
        //given
        var createTaskCommand = CreateTaskCommand
                .builder()
                .comment("Hello world!")
                .partFactor(0.5)
                .jobId(1L)
                .workerId(1L)
                .orderId(1L)
                .createWorkerId(1L)
                .build();

        //when
        var tasks = new ArrayList<Task>() {{
            add(Task.builder().partFactor(0.5).build());
        }};
        when(taskRepository.findAllByOrderId(1L)).thenReturn(tasks);
        when(taskRepository.save(any(Task.class))).then(returnsFirstArg());
        when(orderRepository.findById(1L))
                .thenReturn(Optional.ofNullable(Order.builder().id(1L).build()));
        when(orderRepository.existsById(1L)).thenReturn(true);
        when(workerRepository.findById(1L)).thenReturn(Optional.of(new Worker()));
        when(workerRepository.findByUserId(1L)).thenReturn(Optional.of(new Worker()));
        when(workerRepository.existsById(1L)).thenReturn(true);
        when(jobRepository.findById(1L)).thenReturn(Optional.of(new Job()));
        when(jobRepository.existsById(1L)).thenReturn(true);

        var result = taskService.createTask(createTaskCommand);

        //then
        assertThat(result.getPartFactor()).isEqualTo(0.5);
        assertThat(result.getComment()).isEqualTo("Hello world!");
        assertThat(result.getCreator()).isNotNull();
        assertThat(result.getJob()).isNotNull();
        assertThat(result.getOrder()).isNotNull();
    }

    @Test
    void givenCreateTaskCommandWithPartFactor_createTask_shouldThrowException() {
        //given
        var createTaskCommand = CreateTaskCommand
                .builder()
                .comment("Hello world!")
                .partFactor(0.3)
                .jobId(1L)
                .workerId(1L)
                .orderId(1L)
                .createWorkerId(1L)
                .build();

        //when
        var tasks = new ArrayList<Task>() {{
            add(Task.builder().partFactor(0.5).build());
            add(Task.builder().partFactor(0.4).build());
        }};
        when(taskRepository.findAllByOrderId(1L)).thenReturn(tasks);
        var exception = assertThrows(CreateTaskException.class, () -> taskService.createTask(createTaskCommand));

        //then
        assertThat(exception.getMessage()).isEqualTo("Part factor is too big");
    }

    @Test
    void givenCreateTaskCommandWithWrongOrderId_createTask_shouldThrowException() {
        //given
        var createTaskCommand = CreateTaskCommand
                .builder()
                .comment("Hello world!")
                .partFactor(0.5)
                .jobId(1L)
                .workerId(1L)
                .orderId(1L)
                .createWorkerId(1L)
                .build();

        //when
        when(orderRepository.existsById(1L)).thenReturn(false);
        var exception = assertThrows(CreateTaskException.class, () -> taskService.createTask(createTaskCommand));

        //then
        assertThat(exception.getMessage()).isEqualTo("Order with id = 1 doesn't exist");
    }

    @Test
    void givenCreateTaskCommandWithWrongWorkerId_createTask_shouldThrowException() {
        //given
        var createTaskCommand = CreateTaskCommand
                .builder()
                .comment("Hello world!")
                .partFactor(0.5)
                .jobId(1L)
                .workerId(1L)
                .orderId(1L)
                .createWorkerId(1L)
                .build();

        //when
        when(orderRepository.existsById(1L)).thenReturn(true);
        when(workerRepository.existsById(1L)).thenReturn(false);
        var exception = assertThrows(CreateTaskException.class, () -> taskService.createTask(createTaskCommand));

        //then
        assertThat(exception.getMessage()).isEqualTo("Worker with id = 1 doesn't exist");
    }

    @Test
    void givenCreateTaskCommandWithWrongJobId_createTask_shouldThrowException() {
        //given
        var createTaskCommand = CreateTaskCommand
                .builder()
                .comment("Hello world!")
                .partFactor(0.5)
                .jobId(1L)
                .workerId(1L)
                .orderId(1L)
                .createWorkerId(1L)
                .build();

        //when
        when(orderRepository.existsById(1L)).thenReturn(true);
        when(workerRepository.existsById(1L)).thenReturn(true);
        when(jobRepository.existsById(1L)).thenReturn(false);
        var exception = assertThrows(CreateTaskException.class, () -> taskService.createTask(createTaskCommand));

        //then
        assertThat(exception.getMessage()).isEqualTo("Job with id = 1 doesn't exist");
    }
}
