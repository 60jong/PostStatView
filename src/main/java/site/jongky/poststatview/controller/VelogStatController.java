package site.jongky.poststatview.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.jongky.poststatview.dto.RefreshTokenRegisterRequest;
import site.jongky.poststatview.dto.StatViewParam;
import site.jongky.poststatview.exception.PostStatViewException;
import site.jongky.poststatview.exception.PostStatViewResponseStatus;
import site.jongky.poststatview.service.UserService;
import site.jongky.poststatview.service.VelogStatService;
import site.jongky.poststatview.util.StatViewMaker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static site.jongky.poststatview.exception.PostStatViewResponseStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/velog-stats")
public class VelogStatController {
    @Value("${view.file-path.made-image}")
    private String POST_STAT_VIEW_MADE_IMAGE_FILE_PATH;

    private final UserService userService;
    private final VelogStatService velogStatService;
    private final StatViewMaker statViewMaker;

    // Velog Post Stats Image 반환
    @GetMapping(
            value = "",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte[] showStats(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "show_visitors", required = false) Boolean showVisitors
    ) {
        StatViewParam param = buildStatViewParam(username, showVisitors);
        statViewMaker.makeStatView(param);

        try {
            String statViewImageFileName = String.format(POST_STAT_VIEW_MADE_IMAGE_FILE_PATH, username);

            FileInputStream inputStream = new FileInputStream(statViewImageFileName);
            byte[] postStatsView = IOUtils.toByteArray(inputStream);
            inputStream.close();

            return postStatsView;
        } catch (IOException exception) {
            throw new PostStatViewException(VIEW_MADE_IMAGE_LOAD_FAILURE);
        }
    }

    public StatViewParam buildStatViewParam(String username, Boolean showVisitors) {
        return new StatViewParam(
                username,
                velogStatService.getTotalPosts(username),
                velogStatService.getTags(username),
                showVisitors != null ?
                        velogStatService.getTotalVisitors(username, userService.findRefreshTokenByUsername(username)) : null
        );
    }

    @PostMapping("/users/{username}/token")
    public ResponseEntity<PostStatViewResponseStatus> registerToken(
            @PathVariable(value = "username", required = true) String username,
            @RequestBody RefreshTokenRegisterRequest request
    ) {
        userService.saveWithToken(username, request.getRefreshToken());
        return ResponseEntity.ok(REFRESH_TOKEN_REGISTER_SUCCESS);
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
}
