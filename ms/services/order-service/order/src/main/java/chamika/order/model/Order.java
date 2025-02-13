package chamika.order.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "t_order",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_order_tracking_ref", columnNames = "orderTrackingReferenceString")
        }
)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @SequenceGenerator(name = "order_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id")
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;


    @Column(nullable = false)
    private String orderTrackingReferenceString;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal totalOrderPrice = BigDecimal.ZERO;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipment_address_id")
    private OrderShipmentAddress address;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        generateOrderTrackingReferenceString();
        calculateTotalOrderPrice();
    }

    @PreUpdate
    public void preUpdate() {
        calculateTotalOrderPrice();
    }

    private void calculateTotalOrderPrice() {
        if (orderItems == null || orderItems.isEmpty()) {
            totalOrderPrice = BigDecimal.ZERO;
            return;
        }

        totalOrderPrice = orderItems.stream()
                .map(item -> {
                    if (item.getSubTotal() == null) {
                        item.calculateSubTotal();
                    }
                    return item.getSubTotal();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    // generating a unique tracking reference for the order
    private void generateOrderTrackingReferenceString() {

        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.orderTrackingReferenceString = String.format("ORD-%s-%s", timestamp, uuid);

    }

    public void addOrderItem(OrderItem item) {

        orderItems.add(item);              // Add to list
        item.setOrder(this);           // * Set bidirectional relationship
        calculateTotalOrderPrice(); // Update total

    }

    public void removeOrderItem(OrderItem item) {

        orderItems.remove(item);           // Remove from list
        item.setOrder(null);           // * Break relationship
        calculateTotalOrderPrice(); // Update total
    }


}
