package chamika.user.dto.user;

import chamika.user.dto.address.AddressUpdateReqBody;

public record UserUpdateReqBody(
        String username,
        AddressUpdateReqBody address
) {
}
