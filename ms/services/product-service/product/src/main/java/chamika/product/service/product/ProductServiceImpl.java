package chamika.product.service.product;


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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

//  !  TODO: Need to add image urls from s3 to the database when creating a product - so that we can send them in product response body to front end

    @Override
    @Transactional
    public List<String> uploadProductImages(List<MultipartFile> files) {

        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile image : files) {

            log.info("Uploading product image: {}", image.getOriginalFilename());
            String imageUrl = s3Service.uploadImage(image);
            imageUrls.add(imageUrl);

        }

        return imageUrls;

    }

    @Override
    public ProductResponseBody createProduct(ProductCreateReqBody reqBody, List<String> imageUrls) {

        log.info("Creating a new product: {}", reqBody);

        if (imageUrls.size() > 5) {
            throw new ImageUploadException("Cannot have more than 5 images");
        }

        Category category = categoryRepository.findById(reqBody.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Product product = productMapper.toEntity(reqBody);
        product.setCategory(category);
        product.setProductImageUrls(imageUrls);

        return productMapper.toResponseBody(productRepository.save(product));


    }

    @Override
    @Transactional
    public ProductResponseBody updateProduct(Long id, ProductCreateReqBody reqBody) {

        log.info("Updating product with id: {}", id);

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));



        // Update product
        existingProduct.setTitle(reqBody.title());
        existingProduct.setPrice(reqBody.price());
        existingProduct.setStockLevel(reqBody.stockLevel());
        existingProduct.setDescription(reqBody.description());

//! TODO: -        HOW TO HANDLE IMAGE URLS in update

        existingProduct.setIsInStock(reqBody.isInStock());
        existingProduct.setSupplierId(reqBody.supplierId());


        // Update category if changed
        if (!existingProduct.getCategory().getId().equals(reqBody.categoryId())) {

            Category newCategory = categoryRepository.findById(reqBody.categoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            existingProduct.setCategory(newCategory);
        }

        return productMapper.toResponseBody(productRepository.save(existingProduct));


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
    @Transactional
    public void deleteProduct(Long id) {

        log.info("Deleting product with id: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        // Delete all associated images
        if (product.getProductImageUrls() != null) {
            product.getProductImageUrls().forEach(s3Service::deleteImage);
        }

        productRepository.delete(product);

    }


    @Override
    public PageResponse<ProductResponseBody> searchProducts(String query, int page, int size, String sortBy, String sortDir) {

        log.info("Searching products with query: {}, page: {}, size: {}", query, page, size);

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.searchProducts(query, pageable);

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




    // TODO: Implement update approval status method for stewards to approve or reject products
}
