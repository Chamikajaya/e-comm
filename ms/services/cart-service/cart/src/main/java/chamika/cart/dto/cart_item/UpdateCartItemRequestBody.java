package chamika.cart.dto.cart_item;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateCartItemRequestBody(

        // TODO: fix the bug - cart not updating its total amount after updating a cart item

        @Positive(message = "Quantity should be greater than zero")
        Integer quantity,

        @Positive(message = "Unit price should be greater than zero")
        BigDecimal unitPrice


) {
}
