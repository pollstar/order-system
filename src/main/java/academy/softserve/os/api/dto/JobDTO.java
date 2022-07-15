package academy.softserve.os.api.dto;

import academy.softserve.os.model.Price;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JobDTO {
    private Long id;

    private String description;

    private List<Price> prices;

}
