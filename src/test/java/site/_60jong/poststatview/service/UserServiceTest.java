package site._60jong.poststatview.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import site._60jong.poststatview.domain.User;
import site._60jong.poststatview.respository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    void 토큰과_함께_저장_테스트() {
        // given
        String username = "user1";
        String token = "token1";

        // when
        userService.saveWithToken(username, token);

        // then
        Optional<User> findUser = userRepository.findByUsername(username);
        assertThat(findUser.isPresent()).isTrue();
        assertThat(findUser.get().getUsername()).isEqualTo(username);
    }
}

