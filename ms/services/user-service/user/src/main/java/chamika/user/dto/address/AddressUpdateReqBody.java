package chamika.user.dto.address;

public record AddressUpdateReqBody(
        String street,
        String city,
        String zipCode,
        String country
) {
}
