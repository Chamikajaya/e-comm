package chamika.product.controller;


import chamika.product.dto.category.CategoryCreateReqBody;
import chamika.product.dto.category.CategoryResponseBody;
import chamika.product.dto.product.ProductResponseBody;
import chamika.product.service.category.CategoryServiceImpl;
import chamika.product.shared.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryServiceImpl;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryResponseBody> createCategory(
            @RequestBody @Valid CategoryCreateReqBody request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryServiceImpl.createCategory(request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCategory(
            @PathVariable(name = "id") Long categoryId
    ) {
        categoryServiceImpl.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CategoryResponseBody> updateCategory(
            @PathVariable(name = "id") Long categoryId,
            @RequestBody @Valid CategoryCreateReqBody request
    ) {
        return ResponseEntity.ok(categoryServiceImpl.updateCategory(categoryId, request));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CategoryResponseBody>> getAllCategories() {
        return ResponseEntity.ok(categoryServiceImpl.getAllCategories());
    }


    /* GET ALL PRODUCTS BY CATEGORY */
//    TODO: Double check this method + Sorting Logic
    @GetMapping("/{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageResponse<ProductResponseBody>> getProductsByCategory(
            @PathVariable(name = "id") Long categoryId,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "8", required = false) Integer size,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        PageResponse<ProductResponseBody> response = categoryServiceImpl.getProductsByCategory(categoryId, page, size, sortBy, sortDir);
        return ResponseEntity.ok(response);
    }


}
