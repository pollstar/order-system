package academy.softserve.os.service.impl;

import academy.softserve.os.api.dto.OrderDTO;
import academy.softserve.os.mapper.OrderMapper;
import academy.softserve.os.model.Order;
import academy.softserve.os.repository.OrderRepository;
import academy.softserve.os.service.OrderService;
import academy.softserve.os.service.command.CreateOrderCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper mapper;

    @Override
    @Transactional
    public Optional<OrderDTO> createOrder(CreateOrderCommand command) {
        if (command == null) {
            return Optional.empty();
        }
        Order order = orderRepository.save(mapper.toModel(command));
        return Optional.ofNullable(mapper.toDTO(order));
    }
}
