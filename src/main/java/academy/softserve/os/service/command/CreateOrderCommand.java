package academy.softserve.os.service.command;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CreateOrderCommand {

    private Long clientId;

    private String description;

    private Date placementDate;

    private Date closingDate;

    private Integer phase;

}
