package academy.softserve.os.api.dto.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CreateOrderCommandDTO {

    @NotNull(message = "Field a clientId cannot be null")
    private Long clientId;

    private LocalDateTime placementDate;

    private LocalDateTime closingDate;

    private Integer phase;

    @Length(max = 100, message = "Description is too long")
    private String description;
}
