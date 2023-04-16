package site._60jong.poststatview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class StatViewParam {
    private String username;
    private int posts;
    private List<String> tags;
    private Long visitors;
}
