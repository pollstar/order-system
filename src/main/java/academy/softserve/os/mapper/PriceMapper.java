package academy.softserve.os.mapper;

import academy.softserve.os.api.dto.PriceDTO;
import academy.softserve.os.model.Price;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {
    PriceDTO toDto(Price price);
}
