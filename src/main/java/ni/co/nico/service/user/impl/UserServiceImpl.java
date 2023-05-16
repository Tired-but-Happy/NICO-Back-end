package ni.co.nico.service.user.impl;

import lombok.RequiredArgsConstructor;
import ni.co.nico.domain.User;
import ni.co.nico.repository.user.UserRepository;
import ni.co.nico.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    @Override
    public void login(String address) {
        LOGGER.info("login함수 실행 됨");
        User user = userRepository.findByAddress(address)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setAddress(address);
                    // Perform any other necessary initialization for the new user
                    userRepository.save(newUser);
                    return newUser; // 수정: User 객체를 반환하도록 수정
                });

    }

    @Override
    public User getUserInfo(String userAddress) {
        return null;
    }

    @Override
    public void updateUserInfo(String userAddress) {

    }
}
