package academy.softserve.os.api;

import academy.softserve.os.api.dto.JobDTO;
import academy.softserve.os.api.dto.command.CreateJobCommandDTO;
import academy.softserve.os.exception.JobFindException;
import academy.softserve.os.mapper.JobMapper;
import academy.softserve.os.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;
    private final JobMapper jobMapper;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<JobDTO> createJob(@RequestBody CreateJobCommandDTO commandDTO) {
        var job = jobService.createJob(jobMapper.toModel(commandDTO));
        var jobDto = jobMapper.toDto(job);

        return ResponseEntity.status(HttpStatus.CREATED).body(jobDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        var jobList = jobService.getAllJob().stream().map(jobMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(jobList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{job-id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable("job-id") long id){
        var job = jobService.getJobById(id).orElseThrow(JobFindException::new);
        var jobDto = jobMapper.toDto(job);
        return ResponseEntity.status(HttpStatus.FOUND).body(jobDto);
    }
}
