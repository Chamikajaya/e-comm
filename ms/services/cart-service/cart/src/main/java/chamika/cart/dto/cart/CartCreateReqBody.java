package chamika.cart.dto.cart;

import jakarta.validation.constraints.NotNull;

public record CartCreateReqBody(

        @NotNull(message = "Customer ID is required")
        Long customerId

) {
}
