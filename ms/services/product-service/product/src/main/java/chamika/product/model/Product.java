package chamika.product.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_product")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @SequenceGenerator(name = "product_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id")
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stockLevel;

    private String description;

    @Column(nullable = false)
    private String productImageUrl;

    @Column(nullable = false)
    private Boolean isInStock;

    @Column(nullable = false)
    private String supplierId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductApprovalStatus status = ProductApprovalStatus.PENDING;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


}
