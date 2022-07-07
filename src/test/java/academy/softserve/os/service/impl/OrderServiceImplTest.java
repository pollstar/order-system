package academy.softserve.os.service.impl;

import academy.softserve.os.exception.CreateOrderException;
import academy.softserve.os.mapper.OrderMapper;
import academy.softserve.os.model.Client;
import academy.softserve.os.model.Order;
import academy.softserve.os.repository.OrderRepository;
import academy.softserve.os.service.OrderService;
import academy.softserve.os.service.command.CreateOrderCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


class OrderServiceImplTest {

    private OrderRepository orderRepository;
    private OrderMapper mapper;
    private OrderService orderService;

    @BeforeEach
    public void init() {
        orderRepository = Mockito.mock(OrderRepository.class);
        mapper = Mockito.mock(OrderMapper.class);
        orderService = new OrderServiceImpl(orderRepository, mapper);
    }

    @Test
    void givenValidCreateOrderCommand_createOrder_shouldReturnCreatedOrder() {
        //given
        var client = new Client();
        var placementDate = new Date();
        var closingDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
        Order order = Order.builder()
                .id(1L)
                .client(client)
                .placementDate(placementDate)
                .closingDate(closingDate)
                .description("test")
                .phase(1)
                .build();
        var createOrderCommand = CreateOrderCommand.builder()
                .clientId(1L)
                .placementDate(placementDate)
                .closingDate(closingDate)
                .description("test")
                .phase(1)
                .build();
        //when
        when(mapper.toModel(any(CreateOrderCommand.class))).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        var orderDTO = orderService.createOrder(createOrderCommand);
        //then

        Assertions.assertEquals(1L, orderDTO.getId());
        Assertions.assertEquals(client, orderDTO.getClient());
        Assertions.assertEquals(placementDate, orderDTO.getPlacementDate());
        Assertions.assertEquals(closingDate, orderDTO.getClosingDate());
        Assertions.assertEquals("test", orderDTO.getDescription());
        Assertions.assertEquals(1, orderDTO.getPhase());
    }

    @Test
    void givenFailCreateOrder_createOrder_shouldThrowException() {
        //given
        var client = new Client();
        var placementDate = new Date();
        var closingDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
        Order order = Order.builder()
                .client(client)
                .placementDate(placementDate)
                .closingDate(closingDate)
                .description("test")
                .phase(1)
                .build();
        var createOrderCommand = CreateOrderCommand.builder()
                .clientId(1L)
                .placementDate(placementDate)
                .closingDate(closingDate)
                .description("test")
                .phase(1)
                .build();
        //when
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        //then
        Assertions.assertThrows(CreateOrderException.class, () -> orderService.createOrder(createOrderCommand));
    }
}
