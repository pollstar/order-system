package academy.softserve.os.api;

import academy.softserve.os.api.dto.command.CreateJobCommandDTO;
import academy.softserve.os.mapper.JobMapper;
import academy.softserve.os.model.Job;
import academy.softserve.os.model.Price;
import academy.softserve.os.service.JobService;
import academy.softserve.os.service.command.CreateJobCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {JobController.class, JobMapper.class})
class JobControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobService jobService;

    private final ObjectMapper objectMapper = new ObjectMapper();



    @Test
    void givenValidCreateJobCommandDTO_createJob_shouldCreateNewJobAndReturnOKResponse() throws Exception {
        //given
        var createJobCommandDto = CreateJobCommandDTO.builder()
                .description("test job")
                .clientPrice(new BigDecimal("50.00"))
                .workerPrice(new BigDecimal("40.00"))
                .build();

        //when
        var job = Job.builder()
                .id(1L)
                .description("test job")
                .build();

        var price = Price.builder()
                .workerPrice(new BigDecimal("40.00"))
                .clientPrice(new BigDecimal("50.00"))
                .build();

        var prices = new ArrayList<Price>();
        prices.add(price);
        job.setPrices(prices);

        when(jobService.createJob(any(CreateJobCommand.class))).thenReturn(job);

        //then
        mockMvc.perform(post("/api/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createJobCommandDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.description").value("test job"));
    }
}