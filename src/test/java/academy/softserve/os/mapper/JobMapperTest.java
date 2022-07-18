package academy.softserve.os.mapper;

import academy.softserve.os.model.Job;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JobMapperTest {
    private final JobMapper mapper = Mappers.getMapper(JobMapper.class);

    @Test
    void givenJob_toDto_shouldCorrectlyMapJobToJobDto() {
        //given
        var job = Job.builder()
                .description("Some desc.")
                .id(46L)
                .build();

        //when
        var jobDto = mapper.toDto(job);

        //then
        assertEquals(46L, jobDto.getId());
        assertEquals("Some desc.", jobDto.getDescription());
    }
}
