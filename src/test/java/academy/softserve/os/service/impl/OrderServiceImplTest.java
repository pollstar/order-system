package academy.softserve.os.service.impl;

import academy.softserve.os.exception.CreateOrderException;
import academy.softserve.os.model.Client;
import academy.softserve.os.model.Order;
import academy.softserve.os.repository.ClientRepository;
import academy.softserve.os.repository.OrderRepository;
import academy.softserve.os.service.OrderService;
import academy.softserve.os.service.command.CreateOrderCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


class OrderServiceImplTest {

    private OrderRepository orderRepository;
    private ClientRepository clientRepository;
    private OrderService orderService;

    private LocalDateTime closingDate;
    private LocalDateTime placementDate;

    @BeforeEach
    public void init() {
        orderRepository = Mockito.mock(OrderRepository.class);
        clientRepository = Mockito.mock(ClientRepository.class);
        orderService = new OrderServiceImpl(orderRepository, clientRepository);
        var currentYear = LocalDate.now().getYear();
        var currentMonth = LocalDate.now().getMonth();
        var currentDay = LocalDate.now().getDayOfMonth();
        placementDate = LocalDateTime.now();
        closingDate = LocalDateTime.of(LocalDate.of(currentYear, currentMonth, currentDay + 1), LocalTime.now());
    }

    @Test
    void givenValidCreateOrderCommand_createOrder_shouldReturnCreatedOrder() {
        //given
        var createOrderCommand = CreateOrderCommand.builder()
                .clientId(1L)
                .placementDate(placementDate)
                .closingDate(closingDate)
                .description("test")
                .phase(1)
                .build();
        //when
        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(new Client()));
        when(orderRepository.save(any(Order.class))).then(returnsFirstArg());
        var order = orderService.createOrder(createOrderCommand);
        //then

        assertEquals(placementDate, order.getPlacementDate());
        assertEquals(closingDate, order.getClosingDate());
        assertEquals("test", order.getDescription());
        assertEquals(1, order.getPhase());
    }

    @Test
    void givenFailCreateOrder_createOrder_shouldThrowException() {
        //given
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
        assertThrows(CreateOrderException.class, () -> orderService.createOrder(createOrderCommand));
    }
}
