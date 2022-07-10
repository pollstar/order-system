package academy.softserve.os.service.impl;

import academy.softserve.os.exception.LoginIsNotUniqueException;
import academy.softserve.os.mapper.UserMapper;
import academy.softserve.os.mapper.WorkerMapper;
import academy.softserve.os.model.Worker;
import academy.softserve.os.repository.WorkerRepository;
import academy.softserve.os.service.WorkerService;
import academy.softserve.os.service.command.CreateWorkerCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;

    @Autowired
    WorkerServiceImpl(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Override
    @Transactional
    public Worker createWorker(CreateWorkerCommand createWorkerCommand) {
        var worker = WorkerMapper.INSTANCE.toWorker(createWorkerCommand);
        var user = UserMapper.INSTANCE.toUser(createWorkerCommand);
        worker.setUser(user);
        try {
            return workerRepository.save(worker);
        } catch (RuntimeException e) {
            throw new LoginIsNotUniqueException(e);
        }
    }
}
