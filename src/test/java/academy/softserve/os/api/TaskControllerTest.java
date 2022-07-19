package academy.softserve.os.api;

import academy.softserve.os.api.dto.command.CreateTaskCommandDTO;
import academy.softserve.os.model.Job;
import academy.softserve.os.model.Order;
import academy.softserve.os.model.Task;
import academy.softserve.os.model.Worker;
import academy.softserve.os.service.TaskService;
import academy.softserve.os.service.command.CreateTaskCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TaskControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }


    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(CreateTaskCommandDTO
                                .builder()
                                .comment("Hello world!")
                                .partFactor(0.5)
                                .workerId(1L)
                                .orderId(1L)
                                .build(),
                        "You should specify job id"),
                Arguments.of(CreateTaskCommandDTO
                                .builder()
                                .jobId(1L)
                                .comment("Hello world!")
                                .partFactor(0.5)
                                .workerId(1L)
                                .build(),
                        "You should specify order id"),
                Arguments.of(CreateTaskCommandDTO
                                .builder()
                                .jobId(1L)
                                .comment("Hello world!")
                                .partFactor(0.5)
                                .orderId(1L)
                                .build(),
                        "You should specify worker id"),
                Arguments.of(CreateTaskCommandDTO
                                .builder()
                                .jobId(1L)
                                .comment("Hello world!")
                                .workerId(1L)
                                .orderId(1L)
                                .build(),
                        "You should specify part factor"),
                Arguments.of(
                        CreateTaskCommandDTO
                                .builder()
                                .jobId(1L)
                                .comment("Hello world!")
                                .workerId(1L)
                                .orderId(1L)
                                .partFactor(1.25)
                                .build(),
                        "Part factor value is supposed to be within 0 and 1"),
                Arguments.of(
                        CreateTaskCommandDTO
                                .builder()
                                .jobId(1L)
                                .comment("Hello world!")
                                .workerId(1L)
                                .orderId(1L)
                                .partFactor(-0.5)
                                .build(),
                        "Part factor value is supposed to be within 0 and 1")
        );
    }

    @WithMockUser(value = "someuser", roles = "ADMIN")
    @Test
    void givenWrongAuthority_createTask_shouldThrow403() throws Exception {
        var createTaskCommandDTO = CreateTaskCommandDTO
                .builder()
                .comment("Hello world!")
                .partFactor(0.5)
                .jobId(1L)
                .workerId(1L)
                .orderId(1L)
                .build();
        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTaskCommandDTO)))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(value = "someuser", roles = "WORKER")
    @Test
    void givenCreateTaskCommandDTO_createTask_shouldReturnTaskDTO() throws Exception {
        var createTaskCommandDTO = CreateTaskCommandDTO
                .builder()
                .comment("Hello world!")
                .partFactor(0.5)
                .jobId(1L)
                .workerId(1L)
                .orderId(1L)
                .build();

        var job = new Job();
        job.setId(1L);
        var worker = new Worker();
        worker.setId(1L);
        var order = new Order();
        order.setId(1L);
        var creator = new Worker();
        creator.setId(1L);
        var task = Task.builder()
                .job(job)
                .worker(worker)
                .order(order)
                .creator(creator)
                .comment("Hello world!")
                .partFactor(0.5)
                .build();
        when(taskService.createTask(any(CreateTaskCommand.class))).thenReturn(task);

        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTaskCommandDTO)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.orderId").value("1"))
                .andExpect(jsonPath("$.jobId").value("1"))
                .andExpect(jsonPath("$.workerId").value("1"))
                .andExpect(jsonPath("$.creatorId").value("1"))
                .andExpect(jsonPath("$.partFactor").value("0.5"));
    }

    @WithMockUser(value = "someuser", roles = "WORKER")
    @ParameterizedTest
    @MethodSource("provideArguments")
    void givenWrongDTOData_createTask_shouldThrowException(CreateTaskCommandDTO createTaskCommandDTO, String message) throws Exception {
        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTaskCommandDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Validation failed!"))
                .andExpect(jsonPath("$.details[0]").value(message));
    }
}

