package academy.softserve.os.service.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobCommand {
    private String description;

    private BigDecimal clientPrice;

    private BigDecimal workerPrice;
}
