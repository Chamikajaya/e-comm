package chamika.user.controller;

import chamika.user.dto.user.UserCreateReqBody;
import chamika.user.dto.user.UserResponseBody;
import chamika.user.dto.user.UserUpdateReqBody;
import chamika.user.model.User;
import chamika.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController extends AbstractController<User, Long, UserCreateReqBody, UserUpdateReqBody, UserResponseBody> {

    private final UserService userService;

    @Override
    protected UserResponseBody createEntity(@Valid UserCreateReqBody createDTO) {
        return userService.createUser(createDTO);
    }

    @Override
    protected UserResponseBody getEntity(Long id) {
        return userService.getUser(id);
    }

    @Override
    protected UserResponseBody updateEntity(Long id, @Valid UserUpdateReqBody updateDTO) {
        return userService.updateUser(id, updateDTO);
    }
}