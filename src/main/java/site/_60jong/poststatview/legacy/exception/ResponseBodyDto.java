package site._60jong.poststatview.legacy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseBodyDto {
    private int code;
    private String message;
}
