package academy.softserve.os.service;

import academy.softserve.os.model.Order;
import academy.softserve.os.service.command.CreateOrderCommand;

public interface OrderService {

   Order createOrder(CreateOrderCommand command);
}
