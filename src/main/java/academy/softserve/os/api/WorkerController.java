package academy.softserve.os.api;

import academy.softserve.os.api.dto.WorkerDTO;
import academy.softserve.os.api.dto.command.CreateWorkerCommandDTO;
import academy.softserve.os.mapper.WorkerMapper;
import academy.softserve.os.service.WorkerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WorkerController {
    private static final WorkerMapper WORKER_MAPPER = WorkerMapper.INSTANCE;
    private final WorkerService workerService;

    @Autowired
    WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("api/admin/worker")
    @Operation(summary = "Create a new Worker")
    public ResponseEntity<WorkerDTO> createWorker(@RequestBody @Valid CreateWorkerCommandDTO commandDTO) {
        var createWorkerCommand = WORKER_MAPPER.toCreateWorkerCommand(commandDTO);
        var worker = workerService.createWorker(createWorkerCommand);

        return ResponseEntity.status(HttpStatus.CREATED).body(WORKER_MAPPER.toWorkerDTO(worker));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("api/admin/worker")
    public ResponseEntity<List<WorkerDTO>> getWorkersByName(@RequestParam(name = "name", required = false) String name) {
        if (name == null) {
            return ResponseEntity.status(HttpStatus.OK).body(workerService.getAllWorkers().stream().map(WORKER_MAPPER::toWorkerDTO).collect(Collectors.toList()));
        } else {
            var nameArray = name.split(" ");
            if (nameArray.length == 1) {
               return ResponseEntity.status(HttpStatus.OK).body(workerService.getAllWorkersByName(name, name).stream().map(WORKER_MAPPER::toWorkerDTO).collect(Collectors.toList()));
            } else {
                return  ResponseEntity.status(HttpStatus.OK).body(workerService.getAllWorkersByName(nameArray[0], nameArray[1]).stream().map(WORKER_MAPPER::toWorkerDTO).collect(Collectors.toList()));
            }
        }
    }
}
