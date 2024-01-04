package site._60jong.poststatview.service.velog.request;

import site._60jong.poststatview.domain.AuthInfo;
import site._60jong.poststatview.service.velog.response.posts.PostId;

import java.util.List;
import java.util.stream.Collectors;

public class VelogStatRequestBodyFactory {

    public static VelogStatRequestBody createInitialPostsRequestBody(AuthInfo authInfo) {

        return createPostsRequestBody(authInfo, "");
    }

    public static VelogStatRequestBody createPostsRequestBody(AuthInfo authInfo, String cursor) {

        return VelogStatRequestBody.builder()
                                   .operationName("Posts")
                                   .variable("username", authInfo.getUsername())
                                   .variable("cursor", cursor)
                                   .query(VelogStatQuery.POSTS_QUERY)
                                   .build();
    }

    public static VelogStatRequestBody createGetStatsRequestBody(PostId postId) {

        return VelogStatRequestBody.builder()
                                   .operationName("GetStats")
                                   .variable("post_id", postId.getId())
                                   .query(VelogStatQuery.GET_STATS_QUERY)
                                   .build();
    }

    public static List<List<VelogStatRequestBody>> createGetStatsBatchRequestBodies(List<PostId> postIds) {

        List<VelogStatRequestBody> bodies = postIds.stream()
                                                   .map(VelogStatRequestBodyFactory::createGetStatsRequestBody)
                                                   .collect(Collectors.toList());

        return VelogStatBatchRequestBodyBuilder.createBatchBodies(bodies);
    }

    private static class VelogStatQuery {

        public static final String POSTS_QUERY = "query Posts($username: String, $cursor: ID) { posts(username: $username,cursor: $cursor, limit: 50) { id } }";

        public static final String GET_STATS_QUERY = "query GetStats($post_id: ID!) { getStats(post_id: $post_id) { total } }";
    }
}
