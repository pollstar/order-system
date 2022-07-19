package academy.softserve.os.service;

import academy.softserve.os.model.Order;
import academy.softserve.os.service.command.CreateOrderCommand;

import java.util.List;
import java.util.Optional;

public interface OrderService {

   Order createOrder(CreateOrderCommand command);

   Optional<Order> getOrderById(Long id);

   List<Order> getOrdersByDescription(String description);

   List<Order> getAllOrders();

}
