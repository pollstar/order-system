package academy.softserve.os.service.command;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreateOrderCommand {

    private Long clientId;

    private String description;

    private LocalDateTime placementDate;

    private LocalDateTime closingDate;

    private Integer phase;

}
