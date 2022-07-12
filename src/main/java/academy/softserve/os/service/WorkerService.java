package academy.softserve.os.service;

import academy.softserve.os.model.Worker;
import academy.softserve.os.service.command.CreateWorkerCommand;

public interface WorkerService {
    Worker createWorker(CreateWorkerCommand createWorkerCommand);
}
