package chamika.product.dto.product;

import chamika.product.model.ProductApprovalStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductResponseBody(

        Long id,
        String name,
        String description,
        BigDecimal price,
        Long supplierId,
        Long categoryId,
        Integer stockCount,
        List<String> imageUrls,
        Boolean isInStock,
        ProductApprovalStatus isApproved,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
