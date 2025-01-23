package chamika.product.mapper;


import chamika.product.dto.product.ProductCreateReqBody;
import chamika.product.dto.product.ProductResponseBody;
import chamika.product.model.Category;
import chamika.product.model.Product;
import chamika.product.model.ProductApprovalStatus;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductCreateReqBody request) {
        return Product.builder()
                .title(request.title())
                .description(request.description())
                .price(request.price())
                .supplierId(request.supplierId())
                .stockLevel(request.stockLevel())
                .isInStock(request.isInStock())
                .status(ProductApprovalStatus.PENDING)  // * Default status is PENDING till data steward approves/declines
                .build();
    }


    public ProductResponseBody toResponseBody(Product product) {

        return new ProductResponseBody(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice(),
                product.getSupplierId(),
                product.getCategory().getId(),
                product.getStockLevel(),
                product.getProductImageUrls(),
                product.getIsInStock(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );

    }



}
