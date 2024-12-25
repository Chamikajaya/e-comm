package chamika.cart.service;

import chamika.cart.dto.cart.CartCreateReqBody;
import chamika.cart.dto.cart.CartResponseBody;
import chamika.cart.dto.cart_item.AddToCartRequestBody;
import chamika.cart.exception.DuplicateResourceException;
import chamika.cart.exception.ResourceNotFoundException;
import chamika.cart.mapper.CartMapper;
import chamika.cart.model.Cart;
import chamika.cart.model.CartItem;
import chamika.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;



    @Override
    public CartResponseBody getCartByCustomerId(Long customerId) {

        Cart cart = cartRepository.findByUserId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart not found for customer: " + customerId));

        return cartMapper.toResponseBody(cart);

    }

    @Override
    public CartResponseBody createCart(CartCreateReqBody cartCreateReqBody) {

        // check if cart already exists
        if(cartRepository.existsByUserId(cartCreateReqBody.customerId())){
            throw new DuplicateResourceException("Cart already exists for customer: " + cartCreateReqBody.customerId());
        }

        Cart cart = cartRepository.save(cartMapper.toEntity(cartCreateReqBody));

        return cartMapper.toResponseBody(cart);

    }

    @Override
    public CartResponseBody addItemToCart(Long customerId, AddToCartRequestBody addToCartRequestBody) {

        return null;

//        Cart cart = cartRepository.findByUserId(customerId)
//                .orElseThrow(() -> new ResourceNotFoundException(
//                        "Cart not found for customer: " + customerId));
//
//        // before adding check if item already exists in cart
//        Optional<CartItem> existingItem = cart.getItems()
//                .stream()
//                .filter(cartItem -> cartItem.getProductId().equals(addToCartRequestBody.productId()))
//                .findFirst();
//
//        if(existingItem.isPresent()){
//
//            CartItem cartItem = existingItem.get();
//            cartItem.setQuantity();
//
//        } else {
//
//        }
//

    }

    @Override
    public CartResponseBody resetCart(Long customerId) {
        return null;
    }

    @Override
    public CartResponseBody removeItemFromCart(Long customerId, Long itemId) {
        return null;
    }
}
