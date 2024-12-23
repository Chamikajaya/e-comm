package chamika.product.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record ProductCreateReqBody(
        @NotBlank(message = "Product title is mandatory")
        @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
        String title,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        @NotNull(message = "Price is mandatory")
        @Min(value = 0, message = "Price cannot be negative")
        BigDecimal price,

        @NotNull(message = "Supplier ID is mandatory")
        Long supplierId,

        @NotNull(message = "Category ID is mandatory")
        Long categoryId,

        @NotNull(message = "Stock level is mandatory")
        @Min(value = 0, message = "Stock level cannot be negative")
        Integer stockLevel,

        // This will store S3 URLs after upload
        @Size(min = 1, max = 3, message = "Product must have between 1 and 3 images")
        List<String> productImageUrls,

        @NotNull(message = "Stock status is mandatory")
        Boolean isInStock
) {
}