package chamika.user.controller;


import chamika.user.dto.UserCreateReqBody;
import chamika.user.dto.UserResponseBody;
import chamika.user.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponseBody> createUser(
            @Valid @RequestBody UserCreateReqBody userCreateReqBody
    ) {
        return ResponseEntity.ok(userService.createUser(userCreateReqBody));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseBody> getUser(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseBody> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserCreateReqBody userCreateReqBody
    ) {
        return ResponseEntity.ok(userService.updateUser(id, userCreateReqBody));
    }



}
