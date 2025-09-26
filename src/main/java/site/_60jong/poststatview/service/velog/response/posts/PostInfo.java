package site._60jong.poststatview.service.velog.response.posts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostInfo {

    private String id;
    private List<String> tags;
}
