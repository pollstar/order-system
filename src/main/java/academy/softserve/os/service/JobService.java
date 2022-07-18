package academy.softserve.os.service;

import academy.softserve.os.model.Job;
import academy.softserve.os.service.command.CreateJobCommand;

import java.util.List;

public interface JobService {
    Job createJob(CreateJobCommand command);

    List<Job> getAllJob();
}
