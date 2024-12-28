package chamika.order.service;


import chamika.order.mapper.OrderItemMapper;
import chamika.order.mapper.OrderMapper;
import chamika.order.model.OrderItem;
import chamika.order.repository.OrderItemRepository;
import chamika.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;


}
