package chamika.user.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_address")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {


    @Id
    @SequenceGenerator(name = "address_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_id")
    private long id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String country;

    @OneToOne(mappedBy = "address")
    private User user;

}
