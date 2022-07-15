package academy.softserve.os.service.impl;

import academy.softserve.os.model.Job;
import academy.softserve.os.model.Price;
import academy.softserve.os.repository.JobRepository;
import academy.softserve.os.service.JobService;
import academy.softserve.os.service.PriceService;
import academy.softserve.os.service.command.CreateJobCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final PriceService priceService;

    @Override
    public Job createJob(CreateJobCommand command) {
        var job = jobRepository.save(Job.builder()
                .description(command.getDescription()).build());

        var price = Price.builder()
                .workerPrice(command.getWorkerPrice())
                .clientPrice(command.getClientPrice())
                .dateSince(LocalDate.now())
                .job(job).build();

        priceService.createPrice(price);

        var priceList = priceService.getAllPrice(job);

        job.setPrices(priceList);

        return job;
    }
}
