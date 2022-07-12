package academy.softserve.os.mapper;

import academy.softserve.os.model.Client;
import academy.softserve.os.model.Order;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderMapperTest {


    private final OrderMapper mapper = Mappers.getMapper(OrderMapper.class);

    @Test
    void givenOrderWithClient_toDTO_shouldCorrectlyMapOrderToOrderDTO() {
        var placementDate = LocalDateTime.now();
        var closingDate = LocalDateTime.of(LocalDate
                        .of(
                                LocalDate.now().getYear(),
                                LocalDate.now().getMonth(),
                                LocalDate.now().getDayOfMonth() + 1
                        )
                , LocalTime.now());
        var client = new Client();
        client.setId(1L);
        var order = Order.builder()
                .id(1L)
                .client(client)
                .placementDate(placementDate)
                .closingDate(closingDate)
                .description("test")
                .phase(1)
                .build();

        var orderDTO = mapper.toDTO(order);

        assertEquals(1L, orderDTO.getId());
        assertEquals(1L, orderDTO.getClientId());
        assertEquals(placementDate, orderDTO.getPlacementDate());
        assertEquals(closingDate, orderDTO.getClosingDate());
        assertEquals("test", orderDTO.getDescription());
        assertEquals(1, orderDTO.getPhase());
    }
}