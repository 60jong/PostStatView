package site._60jong.poststatview.service.velog.view;

import java.util.List;

public record VelogStatViewParam(
        String username,
        int posts,
        int visitors,
        List<String> tagNames
) {}
