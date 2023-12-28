package site._60jong.poststatview.service.velog.posts;

import lombok.Getter;

import java.util.List;

@Getter
public class PostsResponse {

    private List<PostId> posts;

    public PostId getLastPostId() {

        return posts.get(posts.size() - 1);
    }

    public boolean isEmpty() {

        return posts.isEmpty();
    }
}
