package chamika.order.dto.order;

import chamika.order.dto.order_item.CreateOrderItemRequest;
import chamika.order.model.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(

        @NotNull(message = "Customer ID is required")
        Long customerId,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,

        @NotNull(message = "Shipping address is required")
        @Valid
        OrderAddressDTO shippingAddress,

        @NotEmpty(message = "Order must contain at least one item")
        @Valid
        List<CreateOrderItemRequest> items
) {
}
