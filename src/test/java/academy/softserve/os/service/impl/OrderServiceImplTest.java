package academy.softserve.os.service.impl;

import academy.softserve.os.exception.CreateOrderException;
import academy.softserve.os.model.Client;
import academy.softserve.os.model.Order;
import academy.softserve.os.repository.ClientRepository;
import academy.softserve.os.repository.OrderRepository;
import academy.softserve.os.service.OrderService;
import academy.softserve.os.service.command.CreateOrderCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


class OrderServiceImplTest {

    private OrderRepository orderRepository;
    private ClientRepository clientRepository;
    private OrderService orderService;

    @BeforeEach
    public void init() {
        orderRepository = Mockito.mock(OrderRepository.class);
        clientRepository = Mockito.mock(ClientRepository.class);
        orderService = new OrderServiceImpl(orderRepository, clientRepository);
    }

    @Test
    void givenValidCreateOrderCommand_createOrder_shouldReturnCreatedOrder() {
        //given
        var placementDate = new Date();
        var closingDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
        Order orderEntity = Order.builder()
                .id(1L)
                .client(new Client())
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
        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(new Client()));
        when(orderRepository.save(any(Order.class))).then(AdditionalAnswers.returnsFirstArg());
        var order = orderService.createOrder(createOrderCommand);
        //then

        Assertions.assertEquals(1L, order.getId());
        Assertions.assertEquals(placementDate, order.getPlacementDate());
        Assertions.assertEquals(closingDate, order.getClosingDate());
        Assertions.assertEquals("test", order.getDescription());
        Assertions.assertEquals(1, order.getPhase());
    }

    @Test
    void givenFailCreateOrder_createOrder_shouldThrowException() {
        //given
        var placementDate = new Date();
        var closingDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
        var createOrderCommand = CreateOrderCommand.builder()
                .clientId(1L)
                .placementDate(placementDate)
                .closingDate(closingDate)
                .description("test")
                .phase(1)
                .build();
        //when
        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(CreateOrderException.class, () -> orderService.createOrder(createOrderCommand));
    }
}
