package academy.softserve.os.api;

import academy.softserve.os.api.dto.TaskDTO;
import academy.softserve.os.api.dto.command.CreateTaskCommandDTO;
import academy.softserve.os.mapper.TaskMapper;
import academy.softserve.os.model.UserDetailsImpl;
import academy.softserve.os.service.TaskService;
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
    @PreAuthorize("hasAnyRole('WORKER')")
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid CreateTaskCommandDTO createTaskCommandDTO,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        var createTaskCommand = taskMapper.toCreateTaskCommand(createTaskCommandDTO);
        System.out.println("command = " + createTaskCommand);
        System.out.println("commandDTO = "+ createTaskCommandDTO);
        if (userDetails instanceof UserDetailsImpl){
            var userId = ((UserDetailsImpl) userDetails).getId();
            createTaskCommand.setCreateWorkerId(userId);
        }
        var user = taskService.createTask(createTaskCommand);
        System.out.println(user);
        var taskDTO = taskMapper.toTaskDTO(user);
        System.out.println(taskDTO);
        return new ResponseEntity<>((taskDTO), HttpStatus.CREATED);
    }

}
