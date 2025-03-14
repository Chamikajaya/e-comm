package chamika.product.repository;

import chamika.product.model.Category;
import chamika.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategory(Category category, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Product> searchProducts(@Param("query") String query, Pageable pageable);


}
