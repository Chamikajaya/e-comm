package chamika.product.service.category;

import chamika.product.dto.category.CategoryCreateReqBody;
import chamika.product.dto.category.CategoryResponseBody;

public interface CategoryService {

    CategoryResponseBody createCategory(CategoryCreateReqBody request);
}
