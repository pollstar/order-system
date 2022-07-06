package academy.softserve.os.service;

import academy.softserve.os.api.dto.OrderDTO;
import academy.softserve.os.service.command.CreateOrderCommand;

import java.util.Optional;

public interface OrderService {

   Optional<OrderDTO> createOrder(CreateOrderCommand command);
}
