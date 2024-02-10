package site._60jong.poststatview.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site._60jong.poststatview.service.auth.AuthService;
import site._60jong.poststatview.service.velog.view.VelogStatViewService;

@RequiredArgsConstructor
@RequestMapping("/api/v2/velog-stat")
@RestController
public class VelogStatApiControllerV2 {

    private final VelogStatViewService statViewService;
    private final AuthService authService;

    @GetMapping(value = "/view",
                produces = "image/svg+xml")
    public ResponseEntity<String> getVelogStatView(
            final @RequestParam String username,
            final @RequestParam(name = "show_visitors", defaultValue = "false") Boolean showVisitors
    ) {
        return ResponseEntity.ok()
                             .cacheControl(CacheControl.noCache())
                             .body(statViewService.getStatView(username, showVisitors));
    }

    @PostMapping("/auth/token")
    public ResponseEntity<String> registerAuth(final @RequestBody AuthRegisterRequest request) {
        authService.register(request);

        return ResponseEntity.ok("SUCCESS");
    }
}
