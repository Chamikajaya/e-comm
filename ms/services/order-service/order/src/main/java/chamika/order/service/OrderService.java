package chamika.order.service;

import chamika.order.dto.order.CreateOrderRequest;
import chamika.order.dto.order.OrderResponse;
import chamika.order.dto.order.OrderStatusUpdateBody;
import jakarta.validation.Valid;

import java.util.List;

public interface OrderService {
    
    OrderResponse createOrder(@Valid CreateOrderRequest request);

    OrderResponse updateOrderStatus(@Valid OrderStatusUpdateBody request, Long orderId);

    List<OrderResponse> getOrdersByCustomerId(Long customerId);

    List<OrderResponse> getAllOrders();
}
