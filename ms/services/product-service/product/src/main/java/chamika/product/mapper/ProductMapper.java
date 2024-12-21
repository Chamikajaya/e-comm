package chamika.product.mapper;


import chamika.product.dto.product.ProductResponseBody;
import chamika.product.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

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

//    TODO: Implement toEntity method


}
