package academy.softserve.os.api;

import academy.softserve.os.api.dto.command.CreateOrderCommandDTO;
import academy.softserve.os.exception.CreateOrderException;
import academy.softserve.os.mapper.OrderMapper;
import academy.softserve.os.model.Client;
import academy.softserve.os.model.Order;
import academy.softserve.os.service.OrderService;
import academy.softserve.os.service.command.CreateOrderCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = {OrderController.class, OrderMapper.class})
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    private final ObjectMapper objectMapper =  new ObjectMapper();
    private  Client client;

    @BeforeEach
    public void init(){
        client = new Client();
    }
    
    @Test
    void givenValidCreateOrderCommandDTO_createOrder_shouldCreateNewOrderAndReturnOKResponse() throws Exception {
        //given
        client.setId(1L);
        var createOrderCommandDTO = CreateOrderCommandDTO.builder()
                .clientId(1L)
                .placementDate(new Date())
                .closingDate( new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)))
                .description("description")
                .phase(1)
                .build();
        //when
        var order = Order.builder()
                .id(1L)
                .client(client)
                .placementDate(new Date())
                .closingDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)))
                .description("description")
                .phase(1)
                .build();

        when(orderService.createOrder(any(CreateOrderCommand.class))).thenReturn(order);
        //then
        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderCommandDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.clientId").value(1L));
    }

    @Test
    void givenCreateOrderCommandDTO_createOrder_ifNotCreatedReturnBadRequest() throws Exception {
        //given
        client.setId(1L);
        var createOrderCommandDTO = CreateOrderCommandDTO.builder()
                .clientId(1L)
                .placementDate(new Date())
                .closingDate( new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)))
                .description("description")
                .phase(1)
                .build();

        //when
        when(orderService.createOrder(any(CreateOrderCommand.class))).thenThrow(CreateOrderException.class);
        //then
        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderCommandDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenCreateOrderCommandDTOWithNullClientId_createOrder_shouldReturnErrorMessageBecauseClientIdCannotBeNull() throws Exception {
        //given
        var createOrderCommandDTO = CreateOrderCommandDTO.builder()
                .clientId(null)
                .placementDate(new Date())
                .closingDate( new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)))
                .description("description")
                .phase(1)
                .build();
        //when
        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderCommandDTO)))
                //then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed!"))
                .andExpect(jsonPath("$.details[0]").value("Field a clientId cannot be null"));
    }

    @Test
    void givenCreateOrderCommandDTOWWithTooLongDescriptionInBody_thenResponseErrorMessage() throws Exception {
        //given
        client.setId(1L);
        String description = "A".repeat(101);
        var createOrderCommandDTO = CreateOrderCommandDTO.builder()
                .clientId(1L)
                .placementDate(new Date())
                .closingDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)))
                .description(description)
                .phase(1)
                .build();
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
