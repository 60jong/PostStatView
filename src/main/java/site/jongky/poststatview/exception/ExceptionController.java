package site.jongky.poststatview.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(PostStatViewException.class)
    private String handlePostStatViewException(PostStatViewException exception) {
        log.info(exception.getResponseStatus().name());

        return new ResponseEntity<>();
    }
}
