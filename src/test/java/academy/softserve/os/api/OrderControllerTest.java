package academy.softserve.os.api;

import academy.softserve.os.api.dto.command.CreateOrderCommandDTO;
import academy.softserve.os.exception.CreateOrderException;
import academy.softserve.os.model.Client;
import academy.softserve.os.model.Order;
import academy.softserve.os.service.OrderService;
import academy.softserve.os.service.command.CreateOrderCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class OrderControllerTest {

    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Client client;
    private LocalDateTime closingDate;
    private LocalDateTime placementDate;
    private CreateOrderCommandDTO createOrderCommandDTO;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        objectMapper.registerModule(new JavaTimeModule());
        client = new Client();
        client.setId(1L);
        placementDate = LocalDateTime.now();
        closingDate = placementDate.plusDays(1);
        createOrderCommandDTO = CreateOrderCommandDTO.builder()
                .clientId(1L)
                .placementDate(placementDate)
                .closingDate(closingDate)
                .description("description")
                .phase(1)
                .build();
    }

    @WithMockUser(value = "someuser", roles = "ADMIN")
    @Test
    void givenValidCreateOrderCommandDTO_createOrder_shouldCreateNewOrderAndReturnOKResponse() throws Exception {
        //when
        var order = Order.builder()
                .id(1L)
                .client(client)
                .placementDate(placementDate)
                .closingDate(closingDate)
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

    @WithMockUser(value = "someuser", roles = "WORKER")
    @Test
    void givenValidCreateOrderCommandDTO_createOrder_shouldReturn403() throws Exception {
        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderCommandDTO)))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(value = "someuser", roles = "ADMIN")
    @Test
    void givenCreateOrderCommandDTO_createOrder_shouldFailBecauseOrderCannotBeCreated() throws Exception {
        //when
        when(orderService.createOrder(any(CreateOrderCommand.class))).thenThrow(CreateOrderException.class);
        //then
        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderCommandDTO)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(value = "someuser", roles = "ADMIN")
    @Test
    void givenCreateOrderCommandDTOWithNullClientId_createOrder_shouldReturnErrorMessageBecauseClientIdCannotBeNull() throws Exception {
        //given
        var createOrderCommandDTO = CreateOrderCommandDTO.builder()
                .placementDate(placementDate)
                .closingDate(closingDate)
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

    @WithMockUser(value = "someuser", roles = "ADMIN")
    @Test
    void givenCreateOrderCommandDTOWWithTooLongDescriptionInBody_createOrder_shouldReturnErrorMessage() throws Exception {
        //given
        String description = "A".repeat(101);
        createOrderCommandDTO.setDescription(description);
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
