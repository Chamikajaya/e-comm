package chamika.product.service.product;

import chamika.product.dto.product.ProductCreateReqBody;
import chamika.product.dto.product.ProductResponseBody;
import chamika.product.shared.PageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ProductResponseBody createProduct(ProductCreateReqBody reqBody);
    PageResponse<ProductResponseBody> getAllProducts(int page, int size, String sortBy, String sortDir);
    ProductResponseBody getProductById(Long id);
    ProductResponseBody updateProduct(Long id, ProductCreateReqBody reqBody);
    void deleteProduct(Long id);
    String uploadProductImage(MultipartFile file);
    List<ProductResponseBody> searchProducts(String query);


}
