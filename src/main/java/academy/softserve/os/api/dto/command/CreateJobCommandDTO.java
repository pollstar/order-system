package academy.softserve.os.api.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobCommandDTO {
    @Length(max = 100, message = "Description is too long")
    private String description;

    private BigDecimal clientPrice;

    private BigDecimal workerPrice;
}
