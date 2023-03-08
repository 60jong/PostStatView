package site.jongky.poststatview.exception;

import lombok.Getter;

@Getter
public enum PostStatViewResponseStatus {
    NO_SUCH_ALGORITHM(1001, "비대칭키 알고리즘 오류");

    private int code;
    private String message;

    PostStatViewResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
