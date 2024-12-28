package chamika.order.mapper;

import chamika.order.dto.order_item.CreateOrderItemRequest;
import chamika.order.dto.order_item.OrderItemResponse;
import chamika.order.model.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public OrderItem toEntity(CreateOrderItemRequest request) {
        return OrderItem.builder()
                .productId(request.productId())
                .quantity(request.quantity())
                .unitPrice(request.unitPrice())
                .build();
    }

    public OrderItemResponse toResponse(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getProductId(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice(),
                orderItem.getSubTotal()
        );
    }
}