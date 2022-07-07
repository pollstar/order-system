package academy.softserve.os.mapper;

import academy.softserve.os.api.dto.OrderDTO;
import academy.softserve.os.api.dto.command.CreateOrderCommandDTO;
import academy.softserve.os.model.Order;
import academy.softserve.os.service.command.CreateOrderCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    CreateOrderCommand toModel(CreateOrderCommandDTO dto);

    @Mapping(target = "clientId", source = "order.client.id")
    OrderDTO toDTO(Order order);

    @Mapping(target = "client.id", source = "dto.clientId")
    Order toModel(CreateOrderCommand dto);
}
