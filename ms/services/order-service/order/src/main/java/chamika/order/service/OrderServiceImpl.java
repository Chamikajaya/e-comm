package chamika.order.service;


import chamika.order.dto.order.CreateOrderRequest;
import chamika.order.dto.order.OrderResponse;
import chamika.order.dto.order.OrderStatusUpdateBody;
import chamika.order.exception.ResourceNotFoundException;
import chamika.order.mapper.OrderItemMapper;
import chamika.order.mapper.OrderMapper;
import chamika.order.model.Order;
import chamika.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;


    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {


        log.info("Creating new order for customer: {}", request.customerId());

        Order order = orderMapper.toEntity(request);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);

    }

    @Override
    public OrderResponse updateOrderStatus(OrderStatusUpdateBody request, Long orderId) {

        log.info("Updating order status for order ID: {} to status: {}", orderId, request.status());

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        // Update status
        order.setStatus(request.status());

        // Save updated order
        Order updatedOrder = orderRepository.save(order);

        // Convert and return response
        return orderMapper.toResponse(updatedOrder);
    }

    @Override
    public List<OrderResponse> getOrdersByCustomerId(Long customerId) {

        log.info("Fetching orders for customer ID: {}", customerId);

        // ? TODO: need to check whether customer exists or not - how do we do that? Could this be done through the BFF ?

        List<Order> ordersOfCustomer = orderRepository.findByCustomerId(customerId);

        return ordersOfCustomer.stream()
                .map(orderMapper::toResponse)
                .toList();

    }

    @Override
    public List<OrderResponse> getAllOrders() {

        log.info("Fetching all orders");

        List<Order> allOrders = orderRepository.findAll();

        return allOrders.stream()
                .map(orderMapper::toResponse)
                .toList();

    }
}
