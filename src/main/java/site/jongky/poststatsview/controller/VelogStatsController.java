package site.jongky.poststatsview.controller;

import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import site.jongky.poststatsview.dto.web.PostTotalReads;
import site.jongky.poststatsview.service.VelogStatsService;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/velog-stats")
public class VelogStatsController {
    private final VelogStatsService velogStatsService;

    @ResponseBody
    @GetMapping(value = "/posts/reads/all")
    public PostTotalReads getPostsTotalReads(@RequestParam("username") String username,
                                   @RequestParam("refresh_token") String refreshToken) throws ParseException {
        List<String> postIds = velogStatsService.findAllPostIdsByUsername(username, refreshToken);

        return new PostTotalReads(velogStatsService.getTotalReads(postIds, refreshToken));
    }

}
