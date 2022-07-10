package academy.softserve.os.service;

import academy.softserve.os.model.Job;
import academy.softserve.os.service.command.CreateJobCommand;

public interface JobService {
    Job createJob(CreateJobCommand command);
}
