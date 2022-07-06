package academy.softserve.os.api;

import academy.softserve.os.api.dto.OrderDTO;
import academy.softserve.os.api.dto.command.CreateOrderCommandDTO;
import academy.softserve.os.mapper.OrderMapper;
import academy.softserve.os.model.Client;
import academy.softserve.os.service.OrderService;
import academy.softserve.os.service.command.CreateOrderCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = {OrderController.class, OrderMapper.class})
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;

    @Test
    void whenPostRequestToOrder_returnNewOrder() throws Exception {
        //given
        var objectMapper = new ObjectMapper();
        Client client = new Client();
        client.setId(1L);
        client.setName("John");
        var createOrderCommandDTO = new CreateOrderCommandDTO(
                client,
                new Date(),
                new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)),
                1,
                "description");
        var orderDTO = new OrderDTO(1L, client,
                new Date(),
                new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)),
                1,
                "description");
        //when
        Mockito.when(orderService.createOrder(Mockito.any(CreateOrderCommand.class))).thenReturn(Optional.of(orderDTO));
        //then
        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderCommandDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.client.id").value(1L))
                .andExpect(jsonPath("$.client.name").value("John"))
                .andExpect(jsonPath("$.description").value("description"));
    }

    @Test
    void whenPostRequestToOrderAndNotCreatedOrder_returnBadRequest() throws Exception {
        //given
        var objectMapper = new ObjectMapper();
        Client client = new Client();
        client.setId(1L);
        client.setName("John");
        var createOrderCommandDTO = new CreateOrderCommandDTO(
                client,
                new Date(),
                new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)),
                1,
                "description");
        //when
        Mockito.when(orderService.createOrder(Mockito.any(CreateOrderCommand.class))).thenReturn(Optional.empty());
        //then
        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderCommandDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostRequestToOrderWithNullClientIntBody_thenResponseErrorMessage() throws Exception {
        //given
        var objectMapper = new ObjectMapper();
        var createOrderCommandDTO = new CreateOrderCommandDTO(
                null,
                new Date(),
                new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)),
                1,
                "description");
        //when
        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderCommandDTO)))
                //then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed!"))
                .andExpect(jsonPath("$.details[0]").value("Client cannot be null"));
    }

    @Test
    void whenPostRequestToOrderWithTooLongDescriptionInBody_thenResponseErrorMessage() throws Exception {
        //given
        var objectMapper = new ObjectMapper();
        Client client = new Client();
        client.setId(1L);
        client.setName("John");
        String description = "A".repeat(101);
        var createOrderCommandDTO = new CreateOrderCommandDTO(
                client,
                new Date(),
                new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)),
                1,
                description);
        //when
        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderCommandDTO)))
                //then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed!"))
                .andExpect(jsonPath("$.details[0]").value("Description is too long"));
    }
}
