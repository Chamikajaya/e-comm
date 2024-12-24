package chamika.cart.dto.cart_item;

import java.math.BigDecimal;

public record CartItemResponseBody(

        Long cartItemId,
        Long productId,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subTotal


) {
}
