package chamika.product.service.product;


import chamika.product.dto.product.ProductCreateReqBody;
import chamika.product.dto.product.ProductResponseBody;
import chamika.product.exception.ResourceNotFoundException;
import chamika.product.mapper.ProductMapper;
import chamika.product.model.Category;
import chamika.product.model.Product;
import chamika.product.repository.CategoryRepository;
import chamika.product.repository.ProductRepository;
import chamika.product.s3.S3Service;
import chamika.product.shared.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final S3Service s3Service;

    @Override
    public ProductResponseBody createProduct(ProductCreateReqBody reqBody) {

        log.info("Creating a new product: {}", reqBody);

        Category category = categoryRepository.findById(reqBody.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + reqBody.categoryId()));

        Product product = productMapper.toEntity(reqBody);

        // Set the category
        product.setCategory(category);

        return productMapper.toResponseBody(productRepository.save(product));


    }

    @Override
    public PageResponse<ProductResponseBody> getAllProducts(int page, int size, String sortBy, String sortDir) {

        log.info("Fetching all products");

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductResponseBody> products = productPage.getContent().stream()
                .map(productMapper::toResponseBody)
                .collect(Collectors.toList());

        return new PageResponse<>(
                products,
                productPage.getNumber(),
                productPage.getTotalPages(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.isFirst(),
                productPage.isLast()
        );


    }

    @Override
    public ProductResponseBody getProductById(Long id) {

        log.info("Fetching product with id: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        return productMapper.toResponseBody(product);
    }

    @Override
    public ProductResponseBody updateProduct(Long id, ProductCreateReqBody reqBody) {

        log.info("Updating product with id: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        Category category = categoryRepository.findById(reqBody.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + reqBody.categoryId()));

        product.setTitle(reqBody.title());
        product.setPrice(reqBody.price());
        product.setStockLevel(reqBody.stockLevel());
        product.setDescription(reqBody.description());
        product.setProductImageUrls(reqBody.productImageUrls());
        product.setIsInStock(reqBody.isInStock());
        product.setSupplierId(reqBody.supplierId());
        product.setCategory(category);

        return productMapper.toResponseBody(productRepository.save(product));


    }

    @Override
    public void deleteProduct(Long id) {

        log.info("Deleting product with id: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        productRepository.delete(product);

    }

    @Override
    public String uploadProductImage(MultipartFile file) {

        // TODO: Implement this later
        return "";
    }

    @Override
    public List<ProductResponseBody> searchProducts(String query) {
        return List.of();
    }

    // TODO: Implement update approval status method for stewards to approve or reject products
}
