package site._60jong.poststatview.service.velog.stat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import site._60jong.poststatview.domain.AuthInfo;
import site._60jong.poststatview.service.velog.response.VelogStat;
import site._60jong.poststatview.service.velog.response.getstats.GetStatsResponse;
import site._60jong.poststatview.service.velog.response.getstats.GetStatsResponses;
import site._60jong.poststatview.service.velog.response.posts.PostInfo;
import site._60jong.poststatview.service.velog.response.posts.PostsResponse;
import site._60jong.poststatview.service.velog.request.VelogStatRequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static site._60jong.poststatview.service.velog.request.VelogStatRequestBodyFactory.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class VelogStatServiceV2 {

    private final VelogRestTemplate velogRestTemplate;
    private final ObjectMapper om;

    /**
     * 유저의 모든 게시글 id를 조회
     */
    public List<PostInfo> findAllPostInfoByAuthInfo(final AuthInfo authInfo) {
        List<PostInfo> postInfos = new ArrayList<>();

        VelogStatRequestBody body = createInitialPostsRequestBody(authInfo);
        PostsResponse postsResponse = getPostsResponse(authInfo, body);

        while (!postsResponse.isEmpty()) {
            postInfos.addAll(postsResponse.getPosts());
            PostInfo lastPostInfo = postsResponse.getLastPostInfo();

            body = createPostsRequestBody(authInfo, lastPostInfo.getId());
            postsResponse = getPostsResponse(authInfo, body);
        }

        return postInfos;
    }

    private PostsResponse getPostsResponse(AuthInfo authInfo, VelogStatRequestBody body) {
        ResponseEntity<VelogStat<PostsResponse>> response = velogRestTemplate.postRequest(authInfo, body);
        VelogStat<PostsResponse> responseBody = response.getBody();

        changeTokenIfRefreshTokenResponded(authInfo, response.getHeaders());

        return om.convertValue(responseBody.getData(), PostsResponse.class);
    }

    private void changeTokenIfRefreshTokenResponded(AuthInfo authInfo, HttpHeaders headers) {
        List<String> setCookies = headers.get(HttpHeaders.SET_COOKIE);

        if (setCookies != null && !setCookies.isEmpty()) {
            for (var cookie : setCookies) {
                String tokenExpression = cookie.split(";")[0];
                String[] tokenTypeAndValue = tokenExpression.split("=");

                if (tokenTypeAndValue.length < 2) {
                    continue;
                }

                final String tokenType = tokenTypeAndValue[0];
                final String tokenValue = tokenTypeAndValue[1];

                if (tokenType.equals("refresh_token")) {
                    authInfo.updateRefreshToken(tokenValue);
                }
            }
        }
    }

    /**
     * 유저의 모든 게시글 방문자 총합을 조회 - batch 조회
     */
    public int batchFindTotalVisitorsByUsername(final AuthInfo authInfo) {
        final List<PostInfo> postInfos = findAllPostInfoByAuthInfo(authInfo);

        return batchFindTotalVisitorsByAuthInfoAndPostIds(authInfo, postInfos);
    }

    public int batchFindTotalVisitorsByAuthInfoAndPostIds(AuthInfo authInfo, List<PostInfo> postInfos) {
        List<List<VelogStatRequestBody>> batchBodies = createGetStatsBatchRequestBodies(postInfos);
        GetStatsResponses statsResponses = getStatsResponses(authInfo, batchBodies);

        return statsResponses.getTotalVisitors();
    }
    private GetStatsResponses getStatsResponses(AuthInfo authInfo, List<List<VelogStatRequestBody>> batchBodies) {
        List<GetStatsResponse> responses = new ArrayList<>();

        for (List<VelogStatRequestBody> batchBody : batchBodies) {
            ResponseEntity<List<VelogStat<GetStatsResponse>>> response = velogRestTemplate.postBatchRequest(authInfo, batchBody);
            List<VelogStat<GetStatsResponse>> responseBodies = response.getBody();

            responses.addAll(getDatas(responseBodies));
        }

        return new GetStatsResponses(responses);
    }

    private List<GetStatsResponse> getDatas(List<VelogStat<GetStatsResponse>> responseBodies) {

        return responseBodies.stream()
                .map(statResponse -> om.convertValue(statResponse.getData(), GetStatsResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * 유저의 모든 게시글 방문자 총합을 조회 - 한 query 씩 조회
     */
    public int findTotalVisitorsByUsername(final AuthInfo authInfo) {
        final List<PostInfo> postInfos = findAllPostInfoByAuthInfo(authInfo);

        return findTotalVisitorsByPostIds(authInfo, postInfos);
    }

    private int findTotalVisitorsByPostIds(AuthInfo authInfo, List<PostInfo> postInfos) {

        List<GetStatsResponse> responses = new ArrayList<>();

        for (PostInfo postInfo : postInfos) {
            VelogStatRequestBody body = createGetStatsRequestBody(postInfo);

            ResponseEntity<VelogStat<GetStatsResponse>> response = velogRestTemplate.postRequest(authInfo, body);
            VelogStat<GetStatsResponse> responseBody = response.getBody();

            responses.add(om.convertValue(responseBody.getData(), GetStatsResponse.class));
        }

        return new GetStatsResponses(responses).getTotalVisitors();
    }
}
