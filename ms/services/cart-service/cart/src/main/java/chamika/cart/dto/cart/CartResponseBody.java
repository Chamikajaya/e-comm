package chamika.cart.dto.cart;

import chamika.cart.dto.cart_item.CartItemResponseBody;

import java.math.BigDecimal;
import java.util.List;

public record CartResponseBody(

        Long id,
        Long customerId,
        List<CartItemResponseBody> items,
        BigDecimal totalAmount

) {
}
