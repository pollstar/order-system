package academy.softserve.os.service.impl;

import academy.softserve.os.model.Job;
import academy.softserve.os.repository.JobRepository;
import academy.softserve.os.service.JobService;
import academy.softserve.os.service.PriceService;
import academy.softserve.os.service.command.CreateJobCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class JobServiceImplTest {
    private JobRepository repository;
    private JobService jobService;
    private PriceService priceService;

    @BeforeEach
    void init(){
        repository = Mockito.mock(JobRepository.class);
        priceService = Mockito.mock(PriceService.class);
        jobService = new JobServiceImpl(repository, priceService);
    }

    @Test
    void givenValidCreateJobCommand_createJob_shouldReturnCreatedJob(){
        var createJobCommand = CreateJobCommand.builder()
                .description("Some job.")
                .clientPrice(new BigDecimal(40))
                .workerPrice(new BigDecimal(30))
                .build();

        when(repository.save(any(Job.class))).then(returnsFirstArg());
        var job = jobService.createJob(createJobCommand);


        System.out.println(job);
        assertEquals("Some job.", job.getDescription());
    }

}