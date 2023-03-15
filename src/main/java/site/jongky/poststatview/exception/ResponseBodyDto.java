package site.jongky.poststatview.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseBodyDto {
    private int code;
    private String message;
}
