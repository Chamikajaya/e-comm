package chamika.cart.dto.cart_item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AddToCartRequestBody(

        @NotNull(message = "Product ID is required")
        Long productId,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity should be greater than zero")
        Integer quantity,

        @NotNull(message = "Unit price is required")
        @Positive(message = "Unit price should be greater than zero")
        BigDecimal unitPrice
) {
}
