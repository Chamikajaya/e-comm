package chamika.user.service;


import chamika.user.dto.user.UserCreateReqBody;
import chamika.user.dto.user.UserResponseBody;
import chamika.user.dto.user.UserUpdateReqBody;
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

        userMapper.updateUserFromDto(existingUser, updateReqBody);
        return userMapper.toResponseBody(userRepository.save(existingUser));

    }



}
