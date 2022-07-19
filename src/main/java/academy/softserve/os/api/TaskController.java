package academy.softserve.os.api;

import academy.softserve.os.api.dto.TaskDTO;
import academy.softserve.os.api.dto.command.CreateTaskCommandDTO;
import academy.softserve.os.mapper.TaskMapper;
import academy.softserve.os.model.User;
import academy.softserve.os.model.UserDetailsImpl;
import academy.softserve.os.service.TaskService;
import academy.softserve.os.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    
    @PostMapping("/api/task")
    @PreAuthorize("hasRole('WORKER')")
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid CreateTaskCommandDTO createTaskCommandDTO,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        var createTaskCommand = taskMapper.toCreateTaskCommand(createTaskCommandDTO);
        if (userDetails instanceof UserDetailsImpl){

            var workerId = ((UserDetailsImpl) userDetails).getWorkerId();
            createTaskCommand.setCreateWorkerId(workerId);
        }
        var user = taskService.createTask(createTaskCommand);
        var taskDTO = taskMapper.toTaskDTO(user);
        return new ResponseEntity<>((taskDTO), HttpStatus.CREATED);
    }

}
