package site._60jong.poststatview.service.velog;

import site._60jong.poststatview.service.velog.posts.PostId;
import site._60jong.poststatview.service.velog.request.VelogStatBatchRequestBody;
import site._60jong.poststatview.service.velog.request.VelogStatBatchRequestBodyBuilder;
import site._60jong.poststatview.service.velog.request.VelogStatRequest;
import site._60jong.poststatview.service.velog.request.VelogStatRequestBody;

import java.util.List;
import java.util.stream.Collectors;

public class VelogStatRequestFactory {

    public static VelogStatRequest createInitialPostsRequest(String username) {

        return createPostsRequest(username, "");
    }

    public static VelogStatRequest createPostsRequest(String username, String cursor) {

        return VelogStatRequest.of(VelogStatRequestBody.builder()
                .operationName("Posts")
                .variable("username", username)
                .variable("cursor", cursor)
                .query(VelogStatQuery.POSTS_QUERY)
                .build());
    }

    public static VelogStatRequest createGetStatsRequest(PostId postId) {

        return VelogStatRequest.of(createGetStatsRequestBody(postId));
    }

    public static List<VelogStatRequest> createGetStatsBatchRequests(List<PostId> postIds) {

        List<VelogStatRequestBody> bodies = postIds.stream()
                                                   .map(VelogStatRequestFactory::createGetStatsRequestBody)
                                                   .collect(Collectors.toList());

//        List<VelogStatRequestBody> batchBodies = VelogStatBatchRequestBody.from(bodies);
        List<List<VelogStatRequestBody>> batchBodies = VelogStatBatchRequestBodyBuilder.createBatchBodies(bodies);
//
//        return batchBodies.stream()
//                          .map(VelogStatRequest::of)
//                          .collect(Collectors.toList());
        return null;
    }

    private static VelogStatRequestBody createGetStatsRequestBody(PostId postId) {

        return VelogStatRequestBody.builder()
                .operationName("GetStats")
                .variable("post_id", postId.getId())
                .query(VelogStatQuery.GET_STATS_QUERY)
                .build();
    }

    private static class VelogStatQuery {

        public static final String POSTS_QUERY = "query Posts($username: String, $cursor: ID) { posts(username: $username,cursor: $cursor, limit: 50) { id } }";

        public static final String GET_STATS_QUERY = "query GetStats($post_id: ID!) { getStats(post_id: $post_id) { total } }";
    }
}
