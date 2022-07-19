package academy.softserve.os.service.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskCommand {
    private Long orderId;
    private Long jobId;
    private Long workerId;
    private Long createWorkerId;
    private Double partFactor;
    private String comment;
}
