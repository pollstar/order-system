package academy.softserve.os.service.impl;

import academy.softserve.os.service.JobService;
import academy.softserve.os.service.command.CreateJobCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class JobServiceImplIntegrationTest {

    @Autowired
    private JobService jobService;

    @BeforeEach
    public void init() {
        jobService.createJob(new CreateJobCommand("test-1", new BigDecimal(10), new BigDecimal(12)));
        jobService.createJob(new CreateJobCommand("test-2", new BigDecimal(11), new BigDecimal(15)));
        jobService.createJob(new CreateJobCommand("tes3", new BigDecimal(10), new BigDecimal(13)));
    }

    @Test
    void getAllJobs_shouldBeReturnAllJobs() {
        var jobs = jobService.findAllJob();

        assertEquals(3, jobs.size());
    }

    @Test
    void getJobById_shouldBeReturnJob() {
        var jobExpect = jobService.createJob(new CreateJobCommand("test-1", new BigDecimal(10), new BigDecimal(12)));
        var job = jobService.findJobById(jobExpect.getId());

        assertEquals(job, Optional.of(jobExpect));
    }
}
