package academy.softserve.os.mapper;

import academy.softserve.os.model.Client;
import academy.softserve.os.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

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
        
        Assertions.assertEquals(1L, orderDTO.getId());
        Assertions.assertEquals(1L, orderDTO.getClientId());
        Assertions.assertEquals(placementDate, orderDTO.getPlacementDate());
        Assertions.assertEquals(closingDate, orderDTO.getClosingDate());
        Assertions.assertEquals("test", orderDTO.getDescription());
        Assertions.assertEquals(1, orderDTO.getPhase());
    }
}