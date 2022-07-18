package academy.softserve.os.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDTO {
    private Long id;

    private String description;

    private List<PriceDTO> prices;

}
