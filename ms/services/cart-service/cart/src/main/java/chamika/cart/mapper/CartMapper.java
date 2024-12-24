package chamika.cart.mapper;


import chamika.cart.dto.cart.CartCreateReqBody;
import chamika.cart.dto.cart.CartResponseBody;
import chamika.cart.dto.cart_item.CartItemResponseBody;
import chamika.cart.model.Cart;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    public Cart toEntity(CartCreateReqBody reqBody) {

        return Cart.builder()
                .userId(reqBody.customerId())
                .build();

    }

    public CartResponseBody toResponseBody(Cart entity) {
        return new CartResponseBody(
                entity.getId(),
                entity.getUserId(),
                entity.getItems().stream()
                        .map(cartItem -> new CartItemResponseBody(
                                cartItem.getId(),
                                cartItem.getProductId(),
                                cartItem.getQuantity(),
                                cartItem.getUnitPrice(),
                                cartItem.getSubTotal(
                                ))
                        ).toList(),
                entity.getTotalAmount()
        );
    }
}
