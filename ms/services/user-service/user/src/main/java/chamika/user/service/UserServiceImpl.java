package chamika.user.service;


import chamika.user.dto.UserCreateReqBody;
import chamika.user.dto.UserResponseBody;
import chamika.user.mapper.UserMapper;
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
                userRepository.findById(id).orElseThrow()
        );
    }

    @Override
    public UserResponseBody updateUser(Long id, UserCreateReqBody userCreateReqBody) {
        return null;
    }




}
