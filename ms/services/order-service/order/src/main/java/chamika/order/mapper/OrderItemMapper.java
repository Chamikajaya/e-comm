package chamika.order.mapper;

import chamika.order.dto.order_item.CreateOrderItemRequest;
import chamika.order.dto.order_item.OrderItemResponse;
import chamika.order.model.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public OrderItem toEntity(CreateOrderItemRequest request) {
    }

    public OrderItemResponse toResponse(OrderItem orderItem) {
    }
}
