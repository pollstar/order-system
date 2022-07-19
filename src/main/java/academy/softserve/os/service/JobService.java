package academy.softserve.os.service;

import academy.softserve.os.model.Job;
import academy.softserve.os.service.command.CreateJobCommand;

import java.util.List;
import java.util.Optional;

public interface JobService {
    Job createJob(CreateJobCommand command);

    List<Job> findAllJob();

    Optional<Job> findJobById(Long id);
}
