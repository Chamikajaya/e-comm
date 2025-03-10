package chamika.product.controller;

import chamika.product.dto.product.ProductApprovalStatusRequest;
import chamika.product.dto.product.ProductCreateReqBody;
import chamika.product.dto.product.ProductResponseBody;
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


    @PostMapping
    public ResponseEntity<ProductResponseBody> createProduct(
            @RequestPart("product") @Valid ProductCreateReqBody reqBody,
            @RequestPart("images") List<MultipartFile> images
    ) {

        List<String> imageUrls = productService.uploadProductImages(images);

        ProductResponseBody product = productService.createProduct(reqBody, imageUrls);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageResponse<ProductResponseBody>> getAllProducts(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        return ResponseEntity.ok(productService.getAllProducts(page, size, sortBy, sortDir, query));

    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponseBody> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") @Valid ProductCreateReqBody reqBody
    ) {
        ProductResponseBody updatedProduct = productService.updateProduct(id, reqBody);
        return ResponseEntity.ok(updatedProduct);
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponseBody> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


   @PatchMapping("/{id}/approve")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponseBody> approveProduct(
            @PathVariable Long id,
            @RequestBody @Valid ProductApprovalStatusRequest reqBody
   ) {
        return ResponseEntity.ok(productService.approveProduct(id, reqBody));
    }



}
