package site._60jong.poststatview.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site._60jong.poststatview.domain.AuthInfo;
import site._60jong.poststatview.repository.AuthInfoRepository;
import site._60jong.poststatview.web.api.AuthRegisterRequest;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthInfoRepository authInfoRepository;

    @Transactional
    public AuthInfo register(AuthRegisterRequest request) {
        final String username = request.getUsername();
        final String token = request.getRefreshToken();
        
        if (existsByUsername(username)) {
            return findAuthInfoAndRegisterToken(username, token);
        }
        return createAuthInfo(username, token);
    }

    public AuthInfo createAuthInfo(String username, String token) {
        AuthInfo authInfo = new AuthInfo(username, token);
        authInfoRepository.save(authInfo);
        return authInfo;
    }

    private AuthInfo findAuthInfoAndRegisterToken(String username, String token) {
        AuthInfo authInfo = findByUsername(username);
        authInfo.registerToken(token);
        
        return authInfo;
    }

    public boolean existsByUsername(String username) {
        return authInfoRepository.existsByUsername(username);
    }

    public AuthInfo findByUsername(String username) {
        return authInfoRepository.findByUsername(username)
                .orElse(null);
    }
}
