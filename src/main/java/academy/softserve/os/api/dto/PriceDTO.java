package academy.softserve.os.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PriceDTO {
    private Long id;

    private BigDecimal clientPrice;

    private BigDecimal workerPrice;

    private LocalDate dateSince;
}
