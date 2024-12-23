package chamika.product.service.category;

import chamika.product.dto.category.CategoryCreateReqBody;
import chamika.product.dto.category.CategoryResponseBody;
import chamika.product.dto.product.ProductResponseBody;
import chamika.product.exception.ResourceNotFoundException;
import chamika.product.mapper.CategoryMapper;
import chamika.product.mapper.ProductMapper;
import chamika.product.model.Category;
import chamika.product.model.Product;
import chamika.product.repository.CategoryRepository;
import chamika.product.repository.ProductRepository;
import chamika.product.shared.PageResponse;
import chamika.product.shared.PaginationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;

    @Override
    public CategoryResponseBody createCategory(CategoryCreateReqBody request) {

        log.info("Creating new category with name: {}", request.name());

        Category category = categoryMapper.toEntity(request);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponseBody(savedCategory);

    }

    @Override
    public List<CategoryResponseBody> getAllCategories() {

        log.info("Fetching all categories");

        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(categoryMapper::toResponseBody)
                .toList();

    }

    @Override
    public CategoryResponseBody updateCategory(Long id, CategoryCreateReqBody request) {

        log.info("Updating category with id: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        category.setName(request.name());
        category.setDescription(request.description());

        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.toResponseBody(updatedCategory);


    }

    @Override
    public void deleteCategory(Long id) {

        log.info("Deleting category with id: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        categoryRepository.delete(category);

    }

    @Override
    public PageResponse<ProductResponseBody> getProductsByCategory(Long categoryId, int page, int size, String sortBy, String sortDir) {

        log.info("Fetching products by category id: {}", categoryId);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));



        Pageable pageable = PaginationUtils.createPageRequest(page, size, sortBy, sortDir);
        
        Page<Product> productPage = productRepository.findByCategory(category, pageable);

        List<ProductResponseBody> productResponseDTOList = productPage.stream()
                .map(productMapper::toResponseBody)
                .toList();

        return new PageResponse<>(
                productResponseDTOList,
                productPage.getNumber(),
                productPage.getTotalPages(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.isFirst(),
                productPage.isLast()
        );
    }


}
