package chamika.user.dto;

import chamika.user.model.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateReqBody(
        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,

        @NotNull(message = "Role is required")
        UserRole role,

        @NotNull(message = "Address is required")
        @Valid
        AddressDto address
) {
}
