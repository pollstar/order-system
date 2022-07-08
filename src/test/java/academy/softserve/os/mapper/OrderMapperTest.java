package academy.softserve.os.mapper;

import academy.softserve.os.model.Client;
import academy.softserve.os.model.Order;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderMapperTest {


    private final OrderMapper mapper = Mappers.getMapper(OrderMapper.class);

    @Test
    void givenOrderWithClient_toDTO_shouldCorrectlyMapOrderToOrderDTO() {
        var placementDate = new Date();
        var closingDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
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