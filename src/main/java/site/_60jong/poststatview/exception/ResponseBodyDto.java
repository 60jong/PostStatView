package site._60jong.poststatview.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseBodyDto {
    private int code;
    private String message;
}
