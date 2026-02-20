package site._60jong.poststatview.service.velog.view;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site._60jong.poststatview.domain.AuthInfo;
import site._60jong.poststatview.service.auth.AuthService;
import site._60jong.poststatview.service.velog.stat.VelogStatServiceV2;
import site._60jong.poststatview.service.velog.response.posts.PostInfo;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VelogStatViewService {

    private final VelogStatServiceV2 statService;
    private final AuthService authService;
    private final SvgStatViewRenderer viewRenderer;

    @Value("${datasource.velog.graphql.user.tag.size.max}")
    private int maxTags;

    public String getStatView(final String username, final Boolean showVisitors) {
        final AuthInfo authInfo = getAuthInfo(username);
        List<PostInfo> postInfos = statService.findAllPostInfoByAuthInfo(authInfo);
        List<String> tagNames = extractTopTagNames(postInfos, maxTags);

        int visitors = findVisitorsIfShowVisitorsAndHasToken(authInfo, postInfos, showVisitors);

        VelogStatViewParam param = new VelogStatViewParam(
                username,
                postInfos.size(),
                visitors,
                tagNames
        );

        return viewRenderer.render(param);
    }

    private int findVisitorsIfShowVisitorsAndHasToken(AuthInfo authInfo, List<PostInfo> postInfos, Boolean showVisitors) {
        if (showVisitors && authInfo.hasRefreshToken()) {
            return statService.batchFindTotalVisitorsByAuthInfoAndPostIds(authInfo, postInfos);
        }
        return 0;
    }

    private AuthInfo getAuthInfo(String username) {
        return authService.findByUsername(username)
                .orElseGet(() -> authService.createAuthInfo(username, null));
    }

    private List<String> extractTopTagNames(List<PostInfo> postInfos, int n) {
        return postInfos.stream()
                .flatMap(info -> info.getTags().stream())
                .collect(Collectors.groupingBy(
                        Function.identity(), // 태그 이름을 key로 사용
                        Collectors.counting()  // 각 그룹의 개수를 count
                ))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
