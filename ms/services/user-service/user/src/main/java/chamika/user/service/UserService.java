package chamika.user.service;

import chamika.user.dto.user.UserCreateReqBody;
import chamika.user.dto.user.UserResponseBody;
import chamika.user.dto.user.UserUpdateReqBody;

public interface UserService {

    UserResponseBody createUser(UserCreateReqBody userCreateReqBody);

    UserResponseBody getUser(Long id);

    UserResponseBody updateUser(Long id, UserUpdateReqBody updateReqBody);
}
