package chamika.product.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateReqBody(

        @NotBlank(message = "Category name is mandatory")
        String name,

        String description
) {
}
