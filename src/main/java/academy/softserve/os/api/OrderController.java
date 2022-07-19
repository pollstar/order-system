package academy.softserve.os.api;

import academy.softserve.os.api.dto.OrderDTO;
import academy.softserve.os.api.dto.command.CreateOrderCommandDTO;
import academy.softserve.os.exception.OrderNotFoundException;
import academy.softserve.os.mapper.OrderMapper;
import academy.softserve.os.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper mapper;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create a new Order")
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody CreateOrderCommandDTO orderCommandDTO) {

        var order = orderService.createOrder(mapper.toModel(orderCommandDTO));
        var orderDto = mapper.toDTO(order);
        return Optional.of(orderDto)
                .map(orderDTO -> ResponseEntity.status(HttpStatus.CREATED).body(orderDTO))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}")
    @Operation(summary = "Get Order by id")
    public ResponseEntity<OrderDTO> getJobById(@PathVariable("id") long id) {
        return orderService.findOrderById(id)
                .map(mapper::toDTO)
                .map(it -> new ResponseEntity(it, HttpStatus.OK)
                .orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Get Order by description")
    public ResponseEntity<List<OrderDTO>> getOrderByDescription(@RequestParam(name = "description", required = false) String description) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.findOrdersByDescription(description).stream().map(mapper::toDTO).collect(Collectors.toList()));

    }
}
