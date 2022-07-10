package academy.softserve.os.api.dto.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CreateJobCommandDTO {
    @Length(max = 100, message = "Description is too long")
    private String description;

    private BigDecimal clientPrice;

    private BigDecimal workerPrice;
}
