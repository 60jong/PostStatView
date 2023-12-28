package site._60jong.poststatview.legacy.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(PostStatViewException.class)
    private ResponseEntity<ResponseBodyDto> handlePostStatViewException(PostStatViewException exception) {
        PostStatViewResponseStatus responseStatus = exception.getResponseStatus();
        return ResponseEntity.ok(
                new ResponseBodyDto(
                        responseStatus.getCode(),
                        responseStatus.getMessage()
                )
        );
    }
}
