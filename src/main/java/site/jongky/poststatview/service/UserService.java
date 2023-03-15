package site.jongky.poststatview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jongky.poststatview.domain.User;
import site.jongky.poststatview.exception.PostStatViewException;
import site.jongky.poststatview.respository.UserRepository;

import static site.jongky.poststatview.exception.PostStatViewResponseStatus.*;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Long saveWithToken(String username, String refreshToken) {
        User user = new User(username, refreshToken);
        return userRepository.save(user);
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
