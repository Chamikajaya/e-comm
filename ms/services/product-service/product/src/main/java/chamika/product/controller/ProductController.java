package chamika.product.controller;

import chamika.product.dto.product.ProductCreateReqBody;
import chamika.product.dto.product.ProductResponseBody;
import chamika.product.exception.ImageUploadException;
import chamika.product.service.product.ProductService;
import chamika.product.shared.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/images")
    public ResponseEntity<List<String>> uploadImages(
            @RequestParam("files") List<MultipartFile> files) {
        if (files.size() > 5) {
            throw new ImageUploadException("Cannot upload more than 5 images");
        }
        List<String> urls = productService.uploadProductImages(files);
        return ResponseEntity.ok(urls);
    }


    // * @RequestPart annotation is used instead of @RequestBody because the createProduct method expects a multipart request containing both a JSON object (ProductCreateReqBody) and a list of files (List<MultipartFile>).
    @PostMapping
    public ResponseEntity<ProductResponseBody> createProduct(
            @RequestPart("product") @Valid ProductCreateReqBody reqBody,
            @RequestPart("images") List<MultipartFile> images
    ) {

        // First upload images
        List<String> imageUrls = productService.uploadProductImages(images);

        // Create product with image URLs
        ProductResponseBody product = productService.createProduct(reqBody, imageUrls);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping
    public ResponseEntity<PageResponse<ProductResponseBody>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(productService.getAllProducts(page, size, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseBody> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseBody> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductCreateReqBody request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<ProductResponseBody>> searchProducts(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(productService.searchProducts(query, page, size, sortBy, sortDir));
    }



}
