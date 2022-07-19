package academy.softserve.os.service.impl;

import academy.softserve.os.exception.CreateTaskException;
import academy.softserve.os.model.Task;
import academy.softserve.os.repository.JobRepository;
import academy.softserve.os.repository.OrderRepository;
import academy.softserve.os.repository.TaskRepository;
import academy.softserve.os.repository.WorkerRepository;
import academy.softserve.os.service.TaskService;
import academy.softserve.os.service.command.CreateTaskCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final OrderRepository orderRepository;
    private final WorkerRepository workerRepository;
    private final JobRepository jobRepository;

    @Override
    @Transactional
    public Task createTask(CreateTaskCommand command) {
        throwIfPartFactorTooBig(command.getPartFactor(), command.getOrderId());
        throwIfAnyDontExist(command.getOrderId(), command.getWorkerId(), command.getJobId());

        var worker = workerRepository.findById(command.getWorkerId()).get();
        var order = orderRepository.findById(command.getOrderId()).get();
        var job = jobRepository.findById(command.getJobId()).get();
        var creatorWorker = workerRepository
                .findById(command.getCreateWorkerId())
                .get();

        var task = Task.builder()
                .comment(command.getComment())
                .creator(creatorWorker)
                .job(job)
                .order(order)
                .worker(worker)
                .partFactor(command.getPartFactor())
                .timeCreate(LocalDateTime.now())
                .build();
        return taskRepository.save(task);
    }

    private void throwIfPartFactorTooBig(Double partFactor, Long orderId) {
        var tasksWithinSameOrder = taskRepository
                .findAllByOrderId(orderId);
        var sumPartFactor = tasksWithinSameOrder
                .stream()
                .mapToDouble(Task::getPartFactor)
                .sum();

        if (partFactor + sumPartFactor > 1) {
            throw new CreateTaskException("Part factor is too big");
        }
    }

    private void throwIfAnyDontExist(Long orderId, Long workerId, Long jobId) {
        if (!orderRepository.existsById(orderId)) {
            throw new CreateTaskException(String.format("Order with id = %d doesn't exist", orderId));
        }
        if (!workerRepository.existsById(workerId)) {
            throw new CreateTaskException(String.format("Worker with id = %d doesn't exist", workerId));
        }
        if (!jobRepository.existsById(jobId)) {
            throw new CreateTaskException(String.format("Job with id = %d doesn't exist", jobId));
        }
    }
}
