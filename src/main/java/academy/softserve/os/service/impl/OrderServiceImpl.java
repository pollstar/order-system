package academy.softserve.os.service.impl;

import academy.softserve.os.exception.CreateOrderException;
import academy.softserve.os.model.Order;
import academy.softserve.os.repository.ClientRepository;
import academy.softserve.os.repository.OrderRepository;
import academy.softserve.os.service.OrderService;
import academy.softserve.os.service.command.CreateOrderCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public Order createOrder(CreateOrderCommand command) {
        var order = clientRepository.findById(command.getClientId())
                .map(client -> Order.builder()
                        .client(client)
                        .placementDate(command.getPlacementDate())
                        .closingDate(command.getClosingDate())
                        .description(command.getDescription())
                        .phase(command.getPhase())
                        .build())
                .orElseThrow(() -> new CreateOrderException("No such a client"));
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findOrdersByDescription(String description) {
        return findAllOrders().stream().filter(order -> order.getDescription().toLowerCase().contains(description.toLowerCase())).collect(Collectors.toList());
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
}
