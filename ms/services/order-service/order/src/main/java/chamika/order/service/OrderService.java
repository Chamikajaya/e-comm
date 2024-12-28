package chamika.order.service;

import chamika.order.dto.order.CreateOrderRequest;
import chamika.order.dto.order.OrderResponse;
import chamika.order.dto.order.OrderStatusUpdateBody;
import jakarta.validation.Valid;

public interface OrderService {
    
    OrderResponse createOrder(@Valid CreateOrderRequest request);

    OrderResponse updateOrderStatus(@Valid OrderStatusUpdateBody request, Long orderId);

    OrderResponse getOrdersByCustomerId(Long customerId);

    OrderResponse getAllOrders();
}
