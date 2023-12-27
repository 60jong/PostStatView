package site._60jong.poststatview.service.velog;

import site._60jong.poststatview.service.VelogStatBatchRequest;
import site._60jong.poststatview.service.VelogStatRequest;
import site._60jong.poststatview.service.VelogStatRequestBody;
import site._60jong.poststatview.service.velog.posts.PostId;

import java.util.List;
import java.util.stream.Collectors;

public class VelogStatRequestFactory {

    public static VelogStatRequest createInitialPostsRequest(String username) {

        return createPostsRequest(username, "");
    }

    public static VelogStatRequest createPostsRequest(String username, String cursor) {

        return VelogStatRequest.from(VelogStatRequestBody.builder()
                                                         .operationName("Posts")
                                                         .variable("username", username)
                                                         .variable("cursor", cursor)
                                                         .query(VelogStatQuery.POSTS_QUERY)
                                                         .build());
    }

    public static VelogStatRequest createGetStatsRequest(PostId postId) {

        return VelogStatRequest.from(VelogStatRequestBody.builder()
                                                         .operationName("GetStats")
                                                         .variable("post_id", postId.getId())
                                                         .query(VelogStatQuery.GET_STATS_QUERY)
                                                         .build());
    }

    private static class VelogStatQuery {

        public static final String POSTS_QUERY = "query Posts($username: String, $cursor: ID) { posts(username: $username,cursor: $cursor, limit: 50) { id } }";

        public static final String GET_STATS_QUERY = "query GetStats($post_id: ID!) { getStats(post_id: $post_id) { total } }";
    }
}
