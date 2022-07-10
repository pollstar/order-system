package academy.softserve.os.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobDTO {
    private Long id;

    private String description;

}
