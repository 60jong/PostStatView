package site._60jong.poststatview.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site._60jong.poststatview.domain.AuthInfo;
import site._60jong.poststatview.repository.AuthInfoRepository;
import site._60jong.poststatview.web.api.AuthRegisterRequest;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthInfoRepository authInfoRepository;

    @Transactional
    public AuthInfo register(AuthRegisterRequest request) {
        final String username = request.username();
        final String token = request.refreshToken();

        return findByUsername(username)
                .map(authInfo -> {
                    authInfo.updateRefreshToken(token);
                    return authInfo;
                })
                .orElseGet(() -> createAuthInfo(username, token));
    }

    public AuthInfo createAuthInfo(String username, String token) {
        AuthInfo authInfo = new AuthInfo(username, token);
        authInfoRepository.save(authInfo);
        return authInfo;
    }

    public Optional<AuthInfo> findByUsername(String username) {
        return authInfoRepository.findByUsername(username);
    }
}
