package chamika.user.mapper;

import chamika.user.dto.AddressDto;
import chamika.user.dto.UserCreateReqBody;
import chamika.user.dto.UserResponseBody;
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
}