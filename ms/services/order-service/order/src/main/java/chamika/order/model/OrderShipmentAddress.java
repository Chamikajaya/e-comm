package chamika.order.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_order_shipment_address")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderShipmentAddress {

    @Id
    @SequenceGenerator(name = "order_shipment_address_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_shipment_address_id")
    private Long id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String country;


}
