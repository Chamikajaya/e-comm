package chamika.product.service.category;

import chamika.product.dto.category.CategoryCreateReqBody;
import chamika.product.dto.category.CategoryResponseBody;
import chamika.product.dto.product.ProductResponseBody;
import chamika.product.shared.PageResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponseBody createCategory(CategoryCreateReqBody request);

    List<CategoryResponseBody> getAllCategories();

    CategoryResponseBody updateCategory(Long id, CategoryCreateReqBody request);

    void deleteCategory(Long id);

    PageResponse<ProductResponseBody> getProductsByCategory(Long categoryId, int page, int size, String sortBy, String sortDir);


}
