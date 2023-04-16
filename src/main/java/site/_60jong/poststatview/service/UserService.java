package site._60jong.poststatview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site._60jong.poststatview.domain.User;
import site._60jong.poststatview.exception.PostStatViewException;
import site._60jong.poststatview.respository.UserRepository;

import static site._60jong.poststatview.exception.PostStatViewResponseStatus.*;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void saveWithToken(String username, String refreshToken) {
        if (exists(username)) {
            findByUsername(username).changeRefreshToken(refreshToken);
            return;
        }

        User user = new User(username, refreshToken);
        userRepository.save(user);
    }

    public boolean exists(String username) {
        return !userRepository.findByUsername(username)
                .isEmpty();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new PostStatViewException(NO_SUCH_USER));
    }

    public String findRefreshTokenByUsername(String username) {
        return findByUsername(username)
                .getRefreshToken();
    }
}
