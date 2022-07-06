package academy.softserve.os.service.command;

import academy.softserve.os.model.Client;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CreateOrderCommand {
    private Client client;

    private String description;

    private Date placementDate;

    private Date closingDate;

    private Integer phase;

}
