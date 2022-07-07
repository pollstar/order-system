package academy.softserve.os.service.impl;

import academy.softserve.os.exception.CreateOrderException;
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
    public Order createOrder(CreateOrderCommand command) {
        return Optional.ofNullable(command)
                .map(then -> orderRepository.save(mapper.toModel(command)))
                .filter(order -> order.getId() != null)
                .orElseThrow(CreateOrderException::new);
    }
}
