package academy.softserve.os.service;

import academy.softserve.os.model.Order;
import academy.softserve.os.service.command.CreateOrderCommand;

import java.util.List;
import java.util.Optional;

public interface OrderService {

   Order createOrder(CreateOrderCommand command);

   Optional<Order> findOrderById(Long id);

   List<Order> findOrdersByDescription(String description);

   List<Order> findAllOrders();

}
