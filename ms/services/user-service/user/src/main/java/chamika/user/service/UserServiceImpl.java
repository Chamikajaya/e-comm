package chamika.user.service;


import chamika.user.dto.user.UserCreateReqBody;
import chamika.user.dto.user.UserResponseBody;
import chamika.user.dto.user.UserUpdateReqBody;
import chamika.user.exception.DuplicateResourceException;
import chamika.user.exception.ResourceNotFoundException;
import chamika.user.mapper.UserMapper;
import chamika.user.model.Address;
import chamika.user.model.User;
import chamika.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseBody createUser(UserCreateReqBody userCreateReqBody) {

        log.info("Creating user with email: {}", userCreateReqBody.email());

        // check if user with the same email already exists
        if (userRepository.existsByEmail(userCreateReqBody.email())) {
            throw new DuplicateResourceException(
                    String.format("User with email: %s already exists", userCreateReqBody.email())
            );
        }

        // check if user with the same username already exists
        if (userRepository.existsByUsername(userCreateReqBody.username())) {
            throw new DuplicateResourceException(
                    String.format("User with username: %s already exists", userCreateReqBody.username())
            );
        }

        return userMapper.toResponseBody(
                userRepository.save(
                        userMapper.toEntity(userCreateReqBody)
                )
        );

    }

    @Override
    public UserResponseBody getUser(Long id) {

        log.info("Getting user with id: {}", id);

        return userMapper.toResponseBody(
                userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                String.format("User not found with id: %d", id)))
        );
    }

    @Override
    public UserResponseBody updateUser(Long id, UserUpdateReqBody updateReqBody) {

        // TODO: Handle email update ??? - for now not allowing email update

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("User not found with id: %d", id)));

        // Check username uniqueness if being updated
        if (
                updateReqBody.username() != null &&  // checking if username is being updated
                !updateReqBody.username().equals(existingUser.getUsername()) &&  // checking if username is being changed that is different from the existing one
                userRepository.existsByUsername(updateReqBody.username())  // checking if the new username already exists
        ) {
            throw new DuplicateResourceException(
                    String.format("Username %s already exists", updateReqBody.username()));
        }

        userMapper.updateUserFromDto(existingUser, updateReqBody);
        return userMapper.toResponseBody(userRepository.save(existingUser));

    }



}
