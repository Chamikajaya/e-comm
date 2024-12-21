package chamika.product.controller;


import chamika.product.dto.category.CategoryCreateReqBody;
import chamika.product.dto.category.CategoryResponseBody;
import chamika.product.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryResponseBody> createCategory(
            @RequestBody @Valid CategoryCreateReqBody request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(request));
    }

}
