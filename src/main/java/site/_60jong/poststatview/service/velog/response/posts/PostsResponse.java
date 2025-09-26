package site._60jong.poststatview.service.velog.response.posts;

import lombok.Getter;

import java.util.List;

@Getter
public class PostsResponse {

    private List<PostInfo> posts;

    public PostInfo getLastPostInfo() {

        return posts.get(posts.size() - 1);
    }

    public boolean isEmpty() {

        return posts.isEmpty();
    }
}
