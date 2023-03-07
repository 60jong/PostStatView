package site.jongky.poststatview.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import site.jongky.poststatview.service.VelogStatService;
import site.jongky.poststatview.util.StatViewMaker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/velog-stats")
public class VelogStatController {
    private final VelogStatService velogStatService;
    private final StatViewMaker statViewMaker;

    // Velog Post Stats Image 반환
    @GetMapping(
            value = "",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte[] showPostStats(@RequestParam("username") String username,
                                @RequestParam("refresh_token") String refreshToken) throws IOException, ParseException {
        String statsViewImageFileName = String.format("statviewimages/%s-post-stats-view.png", username);

        // 이미지가 존재할 경우, 리턴 후 삭제
        if (isStatViewExisting(statsViewImageFileName)) {
            FileInputStream inputStream = new FileInputStream(statsViewImageFileName);
            byte[] postStatsView = IOUtils.toByteArray(inputStream);
            inputStream.close();

            // 이미지 삭제
            File file = new File(statsViewImageFileName);
            file.delete();

            return postStatsView;
        // 이미지가 존재하지 않을 경우, 이미지 생성 후 리턴
        } else {
            statViewMaker.makeStatsView(username, getPostsTotalReads(username, refreshToken));
            FileInputStream inputStream = new FileInputStream(statsViewImageFileName);
            byte[] postStatsView = IOUtils.toByteArray(inputStream);
            inputStream.close();

            return postStatsView;
        }
    }

    public boolean isStatViewExisting(String statsViewImageFileName) throws IOException {
        try {
            // 이미지 존재 여부 확인
            new FileInputStream(statsViewImageFileName).close();
            return true;
        } catch (FileNotFoundException fileNotFoundException) {
            return false;
        } catch (IOException ioException) {
            throw new IOException();
        }
    }

    public Long getPostsTotalReads(String username, String refreshToken) throws ParseException {
        List<String> postIds = velogStatService.findAllPostIdsByUsername(username, refreshToken);

        return velogStatService.getTotalReads(postIds, refreshToken);
    }


}
