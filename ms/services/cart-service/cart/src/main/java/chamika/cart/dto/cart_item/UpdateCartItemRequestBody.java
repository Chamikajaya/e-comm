package chamika.cart.dto.cart_item;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateCartItemRequestBody(

        @Positive(message = "Quantity should be greater than zero")
        Integer quantity,

        @Positive(message = "Unit price should be greater than zero")
        BigDecimal unitPrice


) {
}
