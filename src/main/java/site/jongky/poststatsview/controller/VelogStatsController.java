package site.jongky.poststatsview.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import site.jongky.poststatsview.service.VelogStatsService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/velog-stats")
public class VelogStatsController {
    private final VelogStatsService velogStatsService;
    private final StatsViewMaker statsViewMaker;

    // Velog Post Stats Image 반환
    @GetMapping(
            value = "",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte[] showPostStats(@RequestParam("username") String username,
                                @RequestParam("refresh_token") String refreshToken) throws IOException, ParseException {
        String statsViewImageFileName = String.format("statviewimages/%s-post-stats-view.png", username);

        try {
            new FileInputStream(statsViewImageFileName);
        } catch (FileNotFoundException exception) {
            statsViewMaker.makeStatsView(username, getPostsTotalReads(username, refreshToken));
        } finally {
            return IOUtils.toByteArray(new FileInputStream(statsViewImageFileName));
        }
    }

    public Long getPostsTotalReads(String username, String refreshToken) throws ParseException {
        List<String> postIds = velogStatsService.findAllPostIdsByUsername(username, refreshToken);

        return velogStatsService.getTotalReads(postIds, refreshToken);
    }


}
