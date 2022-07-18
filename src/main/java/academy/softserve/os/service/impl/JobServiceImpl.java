package academy.softserve.os.service.impl;

import academy.softserve.os.model.Job;
import academy.softserve.os.model.Price;
import academy.softserve.os.repository.JobRepository;
import academy.softserve.os.repository.PriceRepository;
import academy.softserve.os.service.JobService;
import academy.softserve.os.service.command.CreateJobCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final PriceRepository priceRepository;

    @Override
    public Job createJob(CreateJobCommand command) {
        var job = jobRepository.save(Job.builder()
                .description(command.getDescription()).build());

        var price = Price.builder()
                .workerPrice(command.getWorkerPrice())
                .clientPrice(command.getClientPrice())
                .dateSince(LocalDate.now())
                .job(job).build();

        priceRepository.save(price);

        var priceList = priceRepository.findAllByJobId(job.getId());

        job.setPrices(priceList);

        return job;
    }

    @Override
    public List<Job> getAllJob() {
        return jobRepository.findAll();
    }

    @Override
    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }
}
