package chamika.user.dto;

import chamika.user.model.UserRole;

public record UserResponseBody (
        long id,
        String username,
        String email,
        UserRole role,
        AddressDto address
) {
}
