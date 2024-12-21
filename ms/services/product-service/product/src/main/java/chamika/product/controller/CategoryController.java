package chamika.product.controller;


import chamika.product.dto.category.CategoryCreateReqBody;
import chamika.product.dto.category.CategoryResponseBody;
import chamika.product.service.category.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//    TODO: Implement Get Products (Paginated) by Category ID

}
