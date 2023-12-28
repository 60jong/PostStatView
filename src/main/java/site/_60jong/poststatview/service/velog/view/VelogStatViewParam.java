package site._60jong.poststatview.service.velog.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class VelogStatViewParam {

    private int posts;
    private int visitors;
    private List<String> tagNames;
}
