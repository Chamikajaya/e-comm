package chamika.order.dto.order;

import chamika.order.model.OrderStatus;
import chamika.order.model.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
         Long id,
         Long customerId,
         OrderStatus status,
         PaymentMethod paymentMethod,
         String orderTrackingReference,
         BigDecimal totalAmount,
         LocalDateTime createdAt,
         LocalDateTime updatedAt,
         OrderAddressDTO shippingAddress,
         List<OrderItemResponseDTO> items
) {
}
