package chamika.product.service;

import chamika.product.dto.category.CategoryCreateReqBody;
import chamika.product.dto.category.CategoryResponseBody;
import chamika.product.repository.CategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // MAPPER

    public CategoryResponseBody createCategory(@Valid CategoryCreateReqBody request) {


    }

    // MAPPER


}
