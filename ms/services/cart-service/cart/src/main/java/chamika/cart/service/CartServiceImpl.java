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
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;


    @Override
    public CartResponseBody getCartByCustomerId(Long customerId) {

        log.info("Fetching cart for customer: {}", customerId);

        Cart cart = findCartByCustomerId(customerId);
        return cartMapper.toResponseBody(cart);
    }

    @Override
    public CartResponseBody createCart(CartCreateReqBody cartCreateReqBody) {

        log.info("Creating cart for customer: {}", cartCreateReqBody.customerId());

        if (cartRepository.existsByUserId(cartCreateReqBody.customerId())) {
            throw new DuplicateResourceException("Cart already exists for customer: " + cartCreateReqBody.customerId());
        }
        Cart cart = cartMapper.toEntity(cartCreateReqBody);
        return saveAndReturnResponse(cart);
    }

    @Override
    public CartResponseBody addItemToCart(Long customerId, AddToCartRequestBody addToCartRequestBody) {

        log.info("Adding item to cart for customer: {}", customerId);

        Cart cart = findCartByCustomerId(customerId);
        CartItem cartItem = cartItemMapper.toEntity(addToCartRequestBody, cart);
        cart.addItem(cartItem);
        return saveAndReturnResponse(cart);
    }

    @Override
    public CartResponseBody updateCartItem(Long customerId, Long itemId, UpdateCartItemRequestBody updateCartItemRequestBody) {

        log.info("Updating item in cart for customer: {}", customerId);

        Cart cart = findCartByCustomerId(customerId);
        CartItem itemToUpdate = findCartItemById(cart, itemId);

        if (updateCartItemRequestBody.quantity() != null) {
            itemToUpdate.setQuantity(updateCartItemRequestBody.quantity());
        }
        if (updateCartItemRequestBody.unitPrice() != null) {
            itemToUpdate.setUnitPrice(updateCartItemRequestBody.unitPrice());
        }

        // recalculate the sub total for the item after updating
        itemToUpdate.findSubTotal();

        // recalculate the total amount for the cart after updating the item
        cart.calculateTotalAmountForCart();

        return saveAndReturnResponse(cart);
    }

    @Override
    public CartResponseBody resetCart(Long customerId) {

        log.info("Resetting cart for customer: {}", customerId);

        Cart cart = findCartByCustomerId(customerId);
        cart.getItems().clear();
        return saveAndReturnResponse(cart);
    }

    @Override
    public CartResponseBody removeItemFromCart(Long customerId, Long itemId) {

        log.info("Removing item from cart for customer: {}", customerId);

        Cart cart = findCartByCustomerId(customerId);
        CartItem itemToRemove = findCartItemById(cart, itemId);
        cart.removeItem(itemToRemove);
        return saveAndReturnResponse(cart);
    }


    // Helper methods to reduce code duplication -->

    // Helper method to find cart
    private Cart findCartByCustomerId(Long customerId) {
        return cartRepository.findByUserId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart not found for customer: " + customerId));
    }

    // Helper method to find cart item
    private CartItem findCartItemById(Cart cart, Long itemId) {
        return cart.getItems()
                .stream()
                .filter(cartItem -> cartItem.getId() == itemId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Item not found in cart: " + itemId));
    }

    // Helper method to save cart and return response
    private CartResponseBody saveAndReturnResponse(Cart cart) {
        cart = cartRepository.save(cart);
        return cartMapper.toResponseBody(cart);
    }


}
