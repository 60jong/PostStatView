package site._60jong.poststatview.service.velog.stat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site._60jong.poststatview.domain.AuthInfo;
import site._60jong.poststatview.service.velog.response.VelogStat;
import site._60jong.poststatview.service.velog.response.getstats.GetStatsResponse;
import site._60jong.poststatview.service.velog.response.getstats.GetStatsResponses;
import site._60jong.poststatview.service.velog.response.posts.PostId;
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

    // TODO : refactoring
    // TODO : trace processing time by aop
    /**
     * 유저의 모든 게시글 id를 조회
     */
    public List<PostId> findAllPostIdByAuthInfo(final AuthInfo authInfo) {

        List<PostId> postIds = new ArrayList<>();

        VelogStatRequestBody body = createInitialPostsRequestBody(authInfo);
        PostsResponse postsResponse = getPostsResponse(authInfo, body);

        while (!postsResponse.isEmpty()) {
            postIds.addAll(postsResponse.getPosts());
            PostId lastPostId = postsResponse.getLastPostId();

            body = createPostsRequestBody(authInfo, lastPostId.getId());
            postsResponse = getPostsResponse(authInfo, body);
        }

        return postIds;
    }

    private PostsResponse getPostsResponse(AuthInfo authInfo, VelogStatRequestBody body) {

        VelogStat<PostsResponse> response = velogRestTemplate.postRequest(authInfo, body);

        return om.convertValue(response.getData(), PostsResponse.class);
    }

    /**
     * 유저의 모든 게시글 방문자 총합을 조회 - batch 조회
     */
    public int batchFindTotalVisitorsByUsername(final AuthInfo authInfo) {
        final List<PostId> postIds = findAllPostIdByAuthInfo(authInfo);

        return batchFindTotalVisitorsByAuthInfoAndPostIds(authInfo, postIds);
    }

    public int batchFindTotalVisitorsByAuthInfoAndPostIds(AuthInfo authInfo, List<PostId> postIds) {
        List<List<VelogStatRequestBody>> batchBodies = createGetStatsBatchRequestBodies(postIds);
        GetStatsResponses statsResponses = getStatsResponses(authInfo, batchBodies);

        return statsResponses.getTotalVisitors();
    }
    private GetStatsResponses getStatsResponses(AuthInfo authInfo, List<List<VelogStatRequestBody>> batchBodies) {
        List<GetStatsResponse> responses = new ArrayList<>();

        for (List<VelogStatRequestBody> batchBody : batchBodies) {
            List<VelogStat<GetStatsResponse>> responseBodies = velogRestTemplate.postBatchRequest(authInfo, batchBody);
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
        final List<PostId> postIds = findAllPostIdByAuthInfo(authInfo);

        return findTotalVisitorsByPostIds(authInfo, postIds);
    }

    private int findTotalVisitorsByPostIds(AuthInfo authInfo, List<PostId> postIds) {

        List<GetStatsResponse> responses = new ArrayList<>();

        for (PostId postId : postIds) {
            VelogStatRequestBody body = createGetStatsRequestBody(postId);

            VelogStat<GetStatsResponse> responseBody = velogRestTemplate.postRequest(authInfo, body);
            responses.add(om.convertValue(responseBody.getData(), GetStatsResponse.class));
        }

        return new GetStatsResponses(responses).getTotalVisitors();
    }
}
