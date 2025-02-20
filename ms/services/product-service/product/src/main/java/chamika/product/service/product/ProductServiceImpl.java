package chamika.product.service.product;


import chamika.product.dto.product.ProductApprovalStatusRequest;
import chamika.product.dto.product.ProductCreateReqBody;
import chamika.product.dto.product.ProductResponseBody;
import chamika.product.exception.ImageUploadException;
import chamika.product.exception.ResourceNotFoundException;
import chamika.product.mapper.ProductMapper;
import chamika.product.model.Category;
import chamika.product.model.Product;
import chamika.product.repository.CategoryRepository;
import chamika.product.repository.ProductRepository;
import chamika.product.s3.S3Service;
import chamika.product.shared.PageResponse;
import chamika.product.shared.PaginationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public List<String> uploadProductImages(List<MultipartFile> files) {

        if (files.size() > 5) {
            throw new ImageUploadException("Cannot have more than 5 images");
        }
        return files.stream()
                .map(file -> {
                    log.info("Uploading product image: {}", file.getOriginalFilename());
                    return s3Service.uploadImage(file);
                })
                .collect(Collectors.toList());

    }



    @Override
    @Transactional
    public ProductResponseBody createProduct(ProductCreateReqBody reqBody, List<String> imageUrls) {

        log.info("Creating a new product: {}", reqBody);

        if (imageUrls.size() > 5) {
            throw new ImageUploadException("Cannot have more than 5 images");
        }

        Category category = categoryRepository.findById(reqBody.categoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Product product = productMapper.toEntity(reqBody);
        product.setCategory(category);
        product.setProductImageUrls(imageUrls);

        return productMapper.toResponseBody(productRepository.save(product));


    }

    @Override
    public ProductResponseBody updateProduct(Long id, ProductCreateReqBody reqBody) {

        log.info("Updating product with id: {}", id);

        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        // Update product details
        existingProduct.setTitle(reqBody.title());
        existingProduct.setPrice(reqBody.price());
        existingProduct.setStockLevel(reqBody.stockLevel());
        existingProduct.setDescription(reqBody.description());
        existingProduct.setIsInStock(reqBody.isInStock());
        existingProduct.setSupplierId(reqBody.supplierId());

        // Handle category updates
        if (!existingProduct.getCategory().getId().equals(reqBody.categoryId())) {
            Category newCategory = categoryRepository.findById(reqBody.categoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            existingProduct.setCategory(newCategory);
        }

        return productMapper.toResponseBody(productRepository.save(existingProduct));

    }


    @Override
    public ProductResponseBody getProductById(Long id) {

        log.info("Fetching product with id: {}", id);

        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
 
        return productMapper.toResponseBody(product);
    }


    @Override
    @Transactional
    public void deleteProduct(Long id) {

        log.info("Deleting product with id: {}", id);

        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        // Delete all associated images from s3
        if (product.getProductImageUrls() != null) {
            product.getProductImageUrls().forEach(s3Service::deleteImage);
        }

        productRepository.delete(product);

    }

    @Override
    public PageResponse<ProductResponseBody> getAllProducts(int page, int size, String sortBy, String sortDir, String query) {

        log.info("Fetching all products");

        Pageable pageable = PaginationUtils.createPageRequest(page, size, sortBy, sortDir);

        Page<Product> productPage;

        // if query is present, search products by title or description else return all products
        if (query != null) {
            productPage = productRepository.searchProducts(query, pageable);
        } else {
            productPage = productRepository.findAll(pageable);
        }

        List<ProductResponseBody> products = productPage.getContent().stream().map(productMapper::toResponseBody).collect(Collectors.toList());

        return new PageResponse<>(products, productPage.getNumber(), productPage.getTotalPages(), productPage.getSize(), productPage.getTotalElements(), productPage.isFirst(), productPage.isLast());


    }


    @Override
    public ProductResponseBody approveProduct(Long id, @Valid ProductApprovalStatusRequest reqBody) {

        log.info("Steward is Approving / Rejecting  product with id: {}", id);

        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        product.setStatus(reqBody.status());

        return productMapper.toResponseBody(productRepository.save(product));


    }
}
