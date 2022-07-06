package academy.softserve.os.service.impl;

import academy.softserve.os.api.dto.OrderDTO;
import academy.softserve.os.mapper.OrderMapper;
import academy.softserve.os.model.Client;
import academy.softserve.os.model.Order;
import academy.softserve.os.repository.OrderRepository;
import academy.softserve.os.service.OrderService;
import academy.softserve.os.service.command.CreateOrderCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@WebMvcTest(value = {OrderServiceImpl.class, OrderMapper.class})
class OrderServiceImplTest {

    @Autowired
    OrderService orderService;

    @MockBean
    OrderRepository orderRepository;

    @Test
    void whenOrderService_createOrder_thenReturnOrderDTO() {
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
                .client(client)
                .placementDate(placementDate)
                .closingDate(closingDate)
                .description("test")
                .phase(1)
                .build();
        //when
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);
        Optional<OrderDTO> orderDTO = orderService.createOrder(createOrderCommand);
        //then
        Assertions.assertTrue(orderDTO.isPresent());
        Assertions.assertEquals(1L, orderDTO.get().getId());
        Assertions.assertEquals(client, orderDTO.get().getClient());
        Assertions.assertEquals(placementDate, orderDTO.get().getPlacementDate());
        Assertions.assertEquals(closingDate, orderDTO.get().getClosingDate());
        Assertions.assertEquals("test", orderDTO.get().getDescription());
        Assertions.assertEquals(1, orderDTO.get().getPhase());
    }

    @Test
    void whenOrderService_createOrder_fallCreate_thenReturnEmpty() {
        //given
        var client = new Client();
        var placementDate = new Date();
        var closingDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
        var createOrderCommand = CreateOrderCommand.builder()
                .client(client)
                .placementDate(placementDate)
                .closingDate(closingDate)
                .description("test")
                .phase(1)
                .build();
        //when
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(null);
        Optional<OrderDTO> orderDTO = orderService.createOrder(createOrderCommand);
        //then
        Assertions.assertFalse(orderDTO.isPresent());
    }
}