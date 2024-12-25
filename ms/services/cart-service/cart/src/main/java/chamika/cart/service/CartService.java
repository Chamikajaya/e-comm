package chamika.cart.service;


import chamika.cart.dto.cart.CartCreateReqBody;
import chamika.cart.dto.cart.CartResponseBody;
import chamika.cart.dto.cart_item.AddToCartRequestBody;
import jakarta.validation.Valid;

public interface CartService {

    CartResponseBody getCartByCustomerId(Long customerId);

    CartResponseBody createCart(@Valid CartCreateReqBody cartCreateReqBody);

    CartResponseBody addItemToCart(Long customerId, @Valid AddToCartRequestBody addToCartRequestBody);

    CartResponseBody resetCart(Long customerId);

    CartResponseBody removeItemFromCart(Long customerId, Long itemId);
}
