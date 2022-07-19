package academy.softserve.os.service;

import academy.softserve.os.model.Task;
import academy.softserve.os.service.command.CreateTaskCommand;

public interface TaskService {

    Task createTask(CreateTaskCommand command);
}
