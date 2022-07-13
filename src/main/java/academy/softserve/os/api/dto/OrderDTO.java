package academy.softserve.os.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderDTO {

    private Long id;

    private Long clientId;

    private LocalDateTime placementDate;

    private LocalDateTime closingDate;

    private Integer phase;

    private String description;
}
