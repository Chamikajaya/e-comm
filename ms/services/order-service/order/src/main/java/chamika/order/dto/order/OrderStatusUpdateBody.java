package chamika.order.dto.order;

import chamika.order.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderStatusUpdateBody(

        @NotNull
        OrderStatus status
) {
}
