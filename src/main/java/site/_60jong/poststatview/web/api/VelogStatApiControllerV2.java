package site._60jong.poststatview.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site._60jong.poststatview.service.velog.view.VelogStatViewService;

@RequiredArgsConstructor
@RequestMapping("/api/v2/velog-stat")
@RestController
public class VelogStatApiControllerV2 {

    private final VelogStatViewService statViewService;

    @GetMapping(value = "/view",
                produces = "image/svg+xml")
    public ResponseEntity<String> getVelogStatView(final @RequestParam String username) {

        return ResponseEntity.ok()
                             .contentType(MediaType.parseMediaType("image/svg+xml"))
                             .body(statViewService.getStatView(username));
    }

}
