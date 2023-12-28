package site._60jong.poststatview.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site._60jong.poststatview.service.velog.stat.VelogStatServiceV2;

@RequiredArgsConstructor
@RequestMapping("/api/v2/velog-stat")
@Controller
public class VelogStatControllerV2 {

    private final VelogStatServiceV2 velogStatService;

    @GetMapping("/visitors/comparison")
    public String getVisitors(final @RequestParam String username, Model model) {

        // Mono
        long startMono = System.currentTimeMillis();
        int visitorsByMono = velogStatService.findTotalVisitorsByUsername(username);
        long endMono = System.currentTimeMillis();

        model.addAttribute("visitorsByMono", visitorsByMono);
        model.addAttribute("elapsedByMono", endMono - startMono);

        // Batching
        long startBatching = System.currentTimeMillis();
        int visitorsByBatching = velogStatService.batchFindTotalVisitorsByUsername(username);
        long endBatching = System.currentTimeMillis();

        model.addAttribute("visitorsByBatching", visitorsByBatching);
        model.addAttribute("elapsedByBatching", endBatching - startBatching);

        return "comparison";
    }
}
