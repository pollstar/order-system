package academy.softserve.os.service;

import academy.softserve.os.model.Worker;
import academy.softserve.os.service.command.CreateWorkerCommand;

import java.util.List;

public interface WorkerService {
    Worker createWorker(CreateWorkerCommand createWorkerCommand);

    List<Worker> getAllWorkers();

    List<Worker> getAllWorkersByName(String name);


}
