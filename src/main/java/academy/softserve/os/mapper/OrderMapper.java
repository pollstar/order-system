package academy.softserve.os.mapper;

import academy.softserve.os.api.dto.OrderDTO;
import academy.softserve.os.api.dto.command.CreateOrderCommandDTO;
import academy.softserve.os.model.Order;
import academy.softserve.os.service.command.CreateOrderCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "dto.client", target = "client")
    CreateOrderCommand toModel(CreateOrderCommandDTO dto);

    OrderDTO toDTO(Order order);

    Order toModel(CreateOrderCommand dto);
}
