package chamika.cart.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_cart_item")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    @Id
    @SequenceGenerator(name = "cart_item_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_item_id")
    private long id;


    @Column(nullable = false)
    private long productId;

    @Column(nullable = false)
    @Min(1)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    private BigDecimal subTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @PrePersist
    @PreUpdate
    public void findSubTotal() {
        if (quantity != null && unitPrice != null) {
            subTotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
    }




}
