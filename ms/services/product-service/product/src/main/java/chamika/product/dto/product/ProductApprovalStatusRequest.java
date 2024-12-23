package chamika.product.dto.product;

import chamika.product.model.ProductApprovalStatus;

public record ProductApprovalStatusRequest(
        ProductApprovalStatus status
) {
}
