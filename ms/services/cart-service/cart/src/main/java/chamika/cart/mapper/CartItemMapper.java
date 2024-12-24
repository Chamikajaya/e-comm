package chamika.cart.mapper;


import chamika.cart.dto.cart_item.AddToCartRequestBody;
import chamika.cart.dto.cart_item.CartItemResponseBody;
import chamika.cart.model.Cart;
import chamika.cart.model.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    public CartItem toEntity(
            AddToCartRequestBody requestBody,
            Cart cart
    ) {

        return CartItem.builder()
                .productId(requestBody.productId())
                .quantity(requestBody.quantity())
                .unitPrice(requestBody.unitPrice())
                .cart(cart)  // * setting cart reference
                .build();
    }

    public CartItemResponseBody toResponseBody(
            CartItem cartItem
    ) {
        return new CartItemResponseBody(
                cartItem.getId(),
                cartItem.getProductId(),
                cartItem.getQuantity(),
                cartItem.getUnitPrice(),
                cartItem.getSubTotal()
        );

    }
}
