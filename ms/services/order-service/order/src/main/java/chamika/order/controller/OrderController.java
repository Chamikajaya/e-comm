package chamika.order.controller;


import chamika.order.dto.order.CreateOrderRequest;
import chamika.order.dto.order.OrderResponse;
import chamika.order.dto.order.OrderStatusUpdateBody;
import chamika.order.service.OrderService;
import chamika.order.service.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody @Valid CreateOrderRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request));
    }


    // Logic ==> Steward-Admins will do order-status update
    @PutMapping("/{orderId}/status")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @RequestBody @Valid OrderStatusUpdateBody request,
            @PathVariable Long orderId
            ) {

        return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrderStatus(request, orderId));
    }


    @GetMapping("/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderResponse>> getOrdersByCustomerId(
            @PathVariable Long customerId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByCustomerId(customerId));
    }


    // get all orders -> for steward-admins
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders());
    }





}
