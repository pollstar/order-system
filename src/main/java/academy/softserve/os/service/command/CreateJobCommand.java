package academy.softserve.os.service.command;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateJobCommand {
    private String description;

    private BigDecimal clientPrice;

    private BigDecimal workerPrice;
}
