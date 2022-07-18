package academy.softserve.os.api;

import academy.softserve.os.api.dto.JobDTO;
import academy.softserve.os.api.dto.command.CreateJobCommandDTO;
import academy.softserve.os.mapper.JobMapper;
import academy.softserve.os.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
