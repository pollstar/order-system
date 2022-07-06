package academy.softserve.os.api;

import academy.softserve.os.api.dto.OrderDTO;
import academy.softserve.os.api.dto.command.CreateOrderCommandDTO;
import academy.softserve.os.mapper.OrderMapper;
import academy.softserve.os.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper mapper;

    @Transactional
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody CreateOrderCommandDTO orderCommandDTO) {
        return orderService.createOrder(mapper.toModel(orderCommandDTO))
                .map(orderDTO -> ResponseEntity.status(HttpStatus.CREATED).body(orderDTO))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
