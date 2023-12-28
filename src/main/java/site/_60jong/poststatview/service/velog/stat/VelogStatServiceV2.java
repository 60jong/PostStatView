package site._60jong.poststatview.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site._60jong.poststatview.service.velog.VelogRestTemplate;
import site._60jong.poststatview.service.velog.response.VelogStat;
import site._60jong.poststatview.service.velog.VelogStatRequestBodyFactory;
import site._60jong.poststatview.service.velog.getstats.GetStatsResponse;
import site._60jong.poststatview.service.velog.getstats.GetStatsResponses;
import site._60jong.poststatview.service.velog.posts.PostId;
import site._60jong.poststatview.service.velog.posts.PostsResponse;
import site._60jong.poststatview.service.velog.request.VelogStatRequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<PostId> findAllPostId(final String username) {

        List<PostId> postIds = new ArrayList<>();

        VelogStatRequestBody body = VelogStatRequestBodyFactory.createInitialPostsRequestBody(username);
        PostsResponse postsResponse = getPostsResponse(body);

        while (!postsResponse.isEmpty()) {
            postIds.addAll(postsResponse.getPosts());
            PostId lastPostId = postsResponse.getLastPostId();

            body = VelogStatRequestBodyFactory.createPostsRequestBody(username, lastPostId.getId());
            postsResponse = getPostsResponse(body);
        }

        return postIds;
    }

    private PostsResponse getPostsResponse(VelogStatRequestBody body) {

        VelogStat<PostsResponse> response = velogRestTemplate.postRequest(body);

        return om.convertValue(response.getData(), PostsResponse.class);
    }

    /**
     * 유저의 모든 게시글 방문자 총합을 조회 - batch 조회
     */
    public int batchFindTotalVisitorsByUsername(final String username) {

        final List<PostId> postIds = findAllPostId(username);

        return batchFindTotalVisitorsByPostIds(postIds);
    }

    private int batchFindTotalVisitorsByPostIds(List<PostId> postIds) {

        List<List<VelogStatRequestBody>> batchBodies = VelogStatRequestBodyFactory.createGetStatsBatchRequestBodies(postIds);
        GetStatsResponses statsResponses = getStatsResponses(batchBodies);

        return statsResponses.getTotalVisitors();
    }
    private GetStatsResponses getStatsResponses(List<List<VelogStatRequestBody>> batchBodies) {

        List<GetStatsResponse> responses = new ArrayList<>();

        for (List<VelogStatRequestBody> batchBody : batchBodies) {
            List<VelogStat<GetStatsResponse>> responseBodies = velogRestTemplate.postBatchRequest(batchBody);
            responses.addAll(getAndConvertAllData(responseBodies));
        }

        return new GetStatsResponses(responses);
    }

    private List<GetStatsResponse> getAndConvertAllData(List<VelogStat<GetStatsResponse>> responseBodies) {

        return responseBodies.stream()
                .map(statResponse -> om.convertValue(statResponse.getData(), GetStatsResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * 유저의 모든 게시글 방문자 총합을 조회 - 한 query 씩 조회
     */
    public int findTotalVisitorsByUsername(final String username) {

        final List<PostId> postIds = findAllPostId(username);

        return findTotalVisitorsByPostIds(postIds);
    }

    public int findTotalVisitorsByPostIds(List<PostId> postIds) {

        List<GetStatsResponse> responses = new ArrayList<>();

        for (PostId postId : postIds) {
            VelogStatRequestBody body = VelogStatRequestBodyFactory.createGetStatsRequestBody(postId);

            VelogStat<GetStatsResponse> responseBody = velogRestTemplate.postRequest(body);
            responses.add(om.convertValue(responseBody.getData(), GetStatsResponse.class));
        }

        return new GetStatsResponses(responses).getTotalVisitors();
    }
}
