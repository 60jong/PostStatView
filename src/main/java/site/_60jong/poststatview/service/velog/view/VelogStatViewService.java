package site._60jong.poststatview.service.velog.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site._60jong.poststatview.domain.AuthInfo;
import site._60jong.poststatview.service.auth.AuthService;
import site._60jong.poststatview.service.velog.stat.VelogStatServiceV2;
import site._60jong.poststatview.service.velog.response.posts.PostId;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VelogStatViewService {

    private final VelogStatServiceV2 statService;
    private final AuthService authService;
    private final SvgStatViewRenderer viewRenderer;

    public String getStatView(final String username, final Boolean showVisitors) {
        final AuthInfo authInfo = getAuthInfo(username);

        List<PostId> postIds = statService.findAllPostIdByAuthInfo(authInfo);
        int visitors = findVisitorsIfShowVisitorsAndHasToken(authInfo, postIds, showVisitors);
        List<String> tagNames = statService.findTopTagNames(3);

        VelogStatViewParam param = new VelogStatViewParam(
                username,
                postIds.size(),
                visitors,
                tagNames
        );

        return viewRenderer.render(param);
    }

    private int findVisitorsIfShowVisitorsAndHasToken(AuthInfo authInfo, List<PostId> postIds, Boolean showVisitors) {
        if (showVisitors && authInfo.hasRefreshToken()) {
            return statService.batchFindTotalVisitorsByAuthInfoAndPostIds(authInfo, postIds);
        }
        return 0;
    }

    private AuthInfo getAuthInfo(String username) {
        if (authService.existsByUsername(username)) {
            return authService.findByUsername(username);
        }
        return createAuthInfoByUsername(username);
    }

    private AuthInfo createAuthInfoByUsername(String username) {
        return authService.createAuthInfo(username, null);
    }
}
