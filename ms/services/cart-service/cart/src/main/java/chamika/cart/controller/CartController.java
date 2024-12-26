package chamika.cart.controller;


import chamika.cart.dto.cart.CartCreateReqBody;
import chamika.cart.dto.cart.CartResponseBody;
import chamika.cart.dto.cart_item.AddToCartRequestBody;
import chamika.cart.dto.cart_item.UpdateCartItemRequestBody;
import chamika.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    // TODO: Later add Abstract Controller

    private final CartService cartService;

    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CartResponseBody> getCartByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.getCartByCustomerId(customerId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CartResponseBody> createCart(@RequestBody @Valid CartCreateReqBody cartCreateReqBody) {
        return ResponseEntity.ok(cartService.createCart(cartCreateReqBody));
    }

    @PostMapping("/{customerId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CartResponseBody> addItemToCart(
            @PathVariable Long customerId,
            @RequestBody @Valid AddToCartRequestBody addToCartRequestBody
    ) {
        return ResponseEntity.ok(cartService.addItemToCart(customerId, addToCartRequestBody));
    }

    @DeleteMapping("/{customerId}/reset")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<CartResponseBody> resetCart(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.resetCart(customerId));
    }

    @DeleteMapping("/{customerId}/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<CartResponseBody> removeItemFromCart(
            @PathVariable Long customerId,
            @PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.removeItemFromCart(customerId, itemId));
    }

    @PostMapping("/{customerId}/items/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CartResponseBody> updateCartItem(
            @PathVariable Long customerId,
            @PathVariable Long itemId,
            @RequestBody @Valid UpdateCartItemRequestBody updateCartItemRequestBody
    ) {
        return ResponseEntity.ok(cartService.updateCartItem(customerId, itemId, updateCartItemRequestBody));
    }


}
