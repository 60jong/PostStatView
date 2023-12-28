package site._60jong.poststatview.legacy.exception;

import lombok.Getter;

@Getter
public class PostStatViewException extends RuntimeException {
    private PostStatViewResponseStatus responseStatus;

    public PostStatViewException(PostStatViewResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
