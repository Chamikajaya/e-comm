package chamika.cart.service;

import chamika.cart.dto.cart.CartCreateReqBody;
import chamika.cart.dto.cart.CartResponseBody;
import chamika.cart.dto.cart_item.AddToCartRequestBody;
import chamika.cart.dto.cart_item.UpdateCartItemRequestBody;
import chamika.cart.exception.DuplicateResourceException;
import chamika.cart.exception.ResourceNotFoundException;
import chamika.cart.mapper.CartItemMapper;
import chamika.cart.mapper.CartMapper;
import chamika.cart.model.Cart;
import chamika.cart.model.CartItem;
import chamika.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;



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

        Cart cart = cartRepository.findByUserId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart not found for customer: " + customerId));

       CartItem cartItem = cartItemMapper.toEntity(addToCartRequestBody, cart);

        cart.addItem(cartItem);

        cart = cartRepository.save(cart);

        return cartMapper.toResponseBody(cart);

    }

    @Override
    public CartResponseBody updateCartItem(Long customerId, Long itemId, UpdateCartItemRequestBody updateCartItemRequestBody) {

        Cart cart = cartRepository.findByUserId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart not found for customer: " + customerId));
        CartItem itemToUpdate = cart.getItems()
                .stream()
                .filter(cartItem -> cartItem.getId() == itemId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Item not found in cart: " + itemId));

        if (updateCartItemRequestBody.quantity() != null) {
            itemToUpdate.setQuantity(updateCartItemRequestBody.quantity());
        }

        if (updateCartItemRequestBody.unitPrice() != null) {
            itemToUpdate.setUnitPrice(updateCartItemRequestBody.unitPrice());
        }

        cart = cartRepository.save(cart);
        return cartMapper.toResponseBody(cart);


    }








    @Override
    public CartResponseBody resetCart(Long customerId) {

        Cart cart = cartRepository.findByUserId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart not found for customer: " + customerId));

        cart.getItems().clear();

        cart = cartRepository.save(cart);

        return cartMapper.toResponseBody(cart);
    }

    @Override
    public CartResponseBody removeItemFromCart(Long customerId, Long itemId) {

        Cart cart = cartRepository.findByUserId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart not found for customer: " + customerId));

        CartItem itemToRemove = cart.getItems()
                .stream()
                .filter(cartItem -> cartItem.getId() == itemId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Item not found in cart: " + itemId));

        cart.removeItem(itemToRemove);

        cart = cartRepository.save(cart);

        return cartMapper.toResponseBody(cart);

    }


}
