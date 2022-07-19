package academy.softserve.os.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long orderId;
    private Long jobId;
    private Long workerId;
    private Long creatorId;
    private Double partFactor;
    private String comment;
    private LocalDateTime timeCreate;
}
