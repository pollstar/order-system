package academy.softserve.os.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class OrderDTO {

    private Long id;

    private Long clientId;

    private Date placementDate;

    private Date closingDate;

    private Integer phase;

    private String description;
}
