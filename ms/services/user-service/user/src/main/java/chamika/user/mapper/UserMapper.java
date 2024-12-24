package chamika.user.mapper;

import chamika.user.dto.address.AddressDto;
import chamika.user.dto.user.UserCreateReqBody;
import chamika.user.dto.user.UserResponseBody;
import chamika.user.dto.user.UserUpdateReqBody;
import chamika.user.model.Address;
import chamika.user.model.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class UserMapper {

    public User toEntity(@Valid UserCreateReqBody reqBody) {

        Address address = Address.builder()
                .street(reqBody.address().street())
                .city(reqBody.address().city())
                .zipCode(reqBody.address().zipCode())
                .country(reqBody.address().country())
                .build();

        return User.builder()
                .username(reqBody.username())
                .email(reqBody.email())
                .role(reqBody.role())
                .address(address)
                .build();
    }

    public UserResponseBody toResponseBody(User user) {

        AddressDto addressDto = new AddressDto(
                user.getAddress().getStreet(),
                user.getAddress().getCity(),
                user.getAddress().getZipCode(),
                user.getAddress().getCountry()
        );

        return new UserResponseBody(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                addressDto
        );
    }

    public void updateUserFromDto(User user, UserUpdateReqBody updateReqBody) {

        if (updateReqBody.username() != null) {
            user.setUsername(updateReqBody.username());
        }

        if (updateReqBody.address() != null) {

            Address address = user.getAddress();

            if (updateReqBody.address().street() != null) {
                address.setStreet(updateReqBody.address().street());
            }
            if (updateReqBody.address().city() != null) {
                address.setCity(updateReqBody.address().city());
            }
            if (updateReqBody.address().zipCode() != null) {
                address.setZipCode(updateReqBody.address().zipCode());
            }
            if (updateReqBody.address().country() != null) {
                address.setCountry(updateReqBody.address().country());
            }
        }
    }
}