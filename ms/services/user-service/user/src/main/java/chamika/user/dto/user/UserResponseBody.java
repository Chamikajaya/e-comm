package chamika.user.dto.user;

import chamika.user.dto.address.AddressDto;
import chamika.user.model.UserRole;

public record UserResponseBody (
        long id,
        String username,
        String email,
        UserRole role,
        AddressDto address
) {
}
