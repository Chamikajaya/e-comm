package chamika.product.dto.category;

import java.time.LocalDateTime;

public record CategoryResponseBody(
        long id,
        String title,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
