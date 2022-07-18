package academy.softserve.os.api.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskCommandDTO {
    @NotNull(message = "You should specify order id")
    private Long orderId;

    @NotNull(message = "You should specify job id")
    private Long jobId;

    @NotNull(message = "You should specify worker id")
    private Long workerId;

    @NotNull(message = "You should specify part factor id")
    @Max(value = 1, message = "Part factor value is supposed to be within 0 and 1")
    @Min(value = 0, message = "Part factor value is supposed to be within 0 and 1")
    private Double partFactor;

    @NotNull(message = "You should specify comment")
    private String comment;
}
