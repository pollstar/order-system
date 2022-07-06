package academy.softserve.os.api.dto.command;

import academy.softserve.os.model.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderCommandDTO {

    @NotNull(message = "Client cannot be null")
    private Client client;

    private Date placementDate;

    private Date closingDate;

    private Integer phase;

    @Length(max = 100, message = "Description is too long")
    private String description;
}
