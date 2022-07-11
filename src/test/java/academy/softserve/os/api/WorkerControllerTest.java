package academy.softserve.os.api;

import academy.softserve.os.api.dto.command.CreateWorkerCommandDTO;
import academy.softserve.os.mapper.WorkerMapper;
import academy.softserve.os.model.User;
import academy.softserve.os.model.Worker;
import academy.softserve.os.service.WorkerService;
import academy.softserve.os.service.command.CreateWorkerCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = {WorkerController.class, WorkerMapper.class})
class WorkerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkerService workerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenCreateWorkerCommand_createWorker_shouldReturnWorkerDTO() throws Exception {
        var createWorkerCommandDTO = CreateWorkerCommandDTO
                .builder()
                .firstName("John")
                .lastName("Smith")
                .login("john123")
                .password("12345")
                .build();

        var user = User
                .builder()
                .login("john123")
                .passwordHash("12345")
                .build();

        var worker = Worker
                .builder()
                .id(1L)
                .firstName("John")
                .lastName("Smith")
                .user(user)
                .build();

        when(workerService.createWorker(any(CreateWorkerCommand.class))).thenReturn(worker);
        mockMvc.perform(post("/api/admin/worker")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createWorkerCommandDTO)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.login").value("john123"));

    }


    @Test
    void givenTooLongLogin_createWorker_shouldThrowException() throws Exception {

        var objectMapper = new ObjectMapper();

        var createWorkerCommand = CreateWorkerCommand
                .builder()
                .firstName("John")
                .lastName("Smith")
                .login("john1231111111111111111111111111111111111111111111111")
                .password("12345")
                .build();

        var createWorkerCommandDTO = CreateWorkerCommandDTO
                .builder()
                .firstName("John")
                .lastName("Smith")
                .login("john1231111111111111111111111111111111111111111111111")
                .password("12345")
                .build();

        var user = User
                .builder()
                .login("john1231111111111111111111111111111111111111111111111")
                .passwordHash("12345")
                .build();

        var worker = Worker
                .builder()
                .id(1L)
                .firstName("John")
                .lastName("Smith")
                .user(user)
                .build();

        when(workerService.createWorker(createWorkerCommand)).thenReturn(worker);

        mockMvc.perform(post("/api/admin/worker")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createWorkerCommandDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenCreateWorkerCommandDTOWithoutFirstname_createWorker_shouldThrowException() throws Exception {

        var objectMapper = new ObjectMapper();

        var createWorkerCommand = CreateWorkerCommand
                .builder()
                .lastName("Smith")
                .login("john190")
                .password("12345")
                .build();

        mockMvc.perform(post("/api/admin/worker")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createWorkerCommand)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Validation failed!"))
                .andExpect(jsonPath("$.details[0]").value("firstName cannot be null"));
    }

    @Test
    void givenCreateWorkerCommandDTOWithoutLastname_createWorker_shouldThrowException() throws Exception {

        var objectMapper = new ObjectMapper();

        var createWorkerCommand = CreateWorkerCommand
                .builder()
                .firstName("John")
                .login("john190")
                .password("12345")
                .build();

        mockMvc.perform(post("/api/admin/worker")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createWorkerCommand)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Validation failed!"))
                .andExpect(jsonPath("$.details[0]").value("lastname cannot be null"));
    }

    @Test
    void givenCreateWorkerCommandDTOWithoutLogin_createWorker_shouldThrowException() throws Exception {

        var objectMapper = new ObjectMapper();

        var createWorkerCommand = CreateWorkerCommand
                .builder()
                .firstName("John")
                .lastName("Smith")
                .password("12345")
                .build();

        mockMvc.perform(post("/api/admin/worker")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createWorkerCommand)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Validation failed!"))
                .andExpect(jsonPath("$.details[0]").value("login cannot be null"));
    }

}