package chamika.order.mapper;

import chamika.order.dto.order.CreateOrderRequest;
import chamika.order.dto.order.OrderResponse;
import chamika.order.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order toEntity (CreateOrderRequest request) {}

    public OrderResponse toResponse (Order order) {}
}
