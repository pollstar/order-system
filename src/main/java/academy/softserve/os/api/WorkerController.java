package academy.softserve.os.api;

import academy.softserve.os.api.dto.WorkerDTO;
import academy.softserve.os.api.dto.command.CreateWorkerCommandDTO;
import academy.softserve.os.exception.LoginIsNotUniqueException;
import academy.softserve.os.mapper.WorkerMapper;
import academy.softserve.os.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class WorkerController {
    private final WorkerService workerService;
    private static final WorkerMapper WORKER_MAPPER = WorkerMapper.INSTANCE;
    @Autowired
    WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @Transactional
    @PostMapping("api/admin/worker")
    public ResponseEntity<WorkerDTO> createWorker(@RequestBody @Valid CreateWorkerCommandDTO commandDTO) {
        var createWorkerCommand = WORKER_MAPPER.toCreateWorkerCommand(commandDTO);
        var worker = workerService.createWorker(createWorkerCommand);

        return ResponseEntity.status(HttpStatus.CREATED).body(WORKER_MAPPER.toWorkerDTO(worker));
    }

    @ExceptionHandler(LoginIsNotUniqueException.class)
    public ResponseEntity<String> handleLoginIsNotUnique(LoginIsNotUniqueException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
