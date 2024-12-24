package chamika.user.service;

import chamika.user.dto.UserCreateReqBody;
import chamika.user.dto.UserResponseBody;
import jakarta.validation.Valid;

public interface UserService {

    UserResponseBody createUser(UserCreateReqBody userCreateReqBody);

    UserResponseBody getUser(Long id);

    UserResponseBody updateUser(Long id, UserCreateReqBody userCreateReqBody);
}
