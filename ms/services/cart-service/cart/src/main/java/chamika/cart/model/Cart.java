package chamika.cart.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_cart")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @SequenceGenerator(name = "cart_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_id")
    private long id;

    @Column(nullable = false)
    private long userId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    private BigDecimal totalAmount = BigDecimal.ZERO;  // Default value of 0 for total amount

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;


    // cart management methods
    public void addItem(CartItem item) {

        items.add(item);              // Add to list
        item.setCart(this);           // * Set bidirectional relationship
        calculateTotalAmountForCart(); // Update total

    }

    public void removeItem(CartItem item) {

        items.remove(item);           // Remove from list
        item.setCart(null);           // * Break relationship
        calculateTotalAmountForCart(); // Update total
    }

    @PrePersist  // Calculate total amount before persisting
    @PreUpdate  // Calculate total amount before updating
    public void calculateTotalAmountForCart() {

        totalAmount = items.stream()
                .map(CartItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
