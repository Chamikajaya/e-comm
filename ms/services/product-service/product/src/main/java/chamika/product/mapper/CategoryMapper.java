package chamika.product.mapper;

import chamika.product.dto.category.CategoryCreateReqBody;
import chamika.product.dto.category.CategoryResponseBody;
import chamika.product.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryCreateReqBody reqBody) {
        return Category.builder()
                .name(reqBody.name())
                .description(reqBody.description())
                .build();
    }

    public CategoryResponseBody toResponseBody(Category category) {
        return new CategoryResponseBody(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }

}
