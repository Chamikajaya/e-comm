package chamika.order.service;


import chamika.order.dto.order.CreateOrderRequest;
import chamika.order.dto.order.OrderResponse;
import chamika.order.dto.order.OrderStatusUpdateBody;
import chamika.order.mapper.OrderItemMapper;
import chamika.order.mapper.OrderMapper;
import chamika.order.repository.OrderItemRepository;
import chamika.order.repository.OrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl  implements OrderService {

//    private final OrderMapper orderMapper;
//    private final OrderRepository orderRepository;
//
//    private final OrderItemMapper orderItemMapper;
//    private final OrderItemRepository orderItemRepository;


    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        return null;
    }

    @Override
    public OrderResponse updateOrderStatus(OrderStatusUpdateBody request, Long orderId) {
        return null;
    }

    @Override
    public OrderResponse getOrdersByCustomerId(Long customerId) {
        return null;
    }

    @Override
    public OrderResponse getAllOrders() {
        return null;
    }
}
