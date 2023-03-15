package site.jongky.poststatview.util;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.jongky.poststatview.dto.StatViewParam;
import site.jongky.poststatview.exception.PostStatViewException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

import static site.jongky.poststatview.exception.PostStatViewResponseStatus.*;

@Component
public class StatViewMaker {
    @Value("${view.file-path.background}")
    private String POST_STAT_VIEW_BACKGROUND_IMAGE_FILE_PATH;
    @Value("${view.file-path.made-image}")
    private String POST_STAT_VIEW_MADE_IMAGE_FILE_PATH;
    private final String POST_STAT_VIEW_SUBJECT_SUFFIX = "\'s velog Stats";
    private final String POST_STAT_VIEW_VISITORS_PREFIX = "Visitors : ";
    private final String POST_STAT_VIEW_POSTS_PREFIX = "Posts : ";
    private final String POST_STAT_VIEW_TAGS_PREFIX = "Tags : ";

    private Graphics graphics;

    public byte[] makeStatView(StatViewParam statViewParam) {
        String username = statViewParam.getUsername();
        int posts = statViewParam.getPosts();
        List<String> tags = statViewParam.getTags();
        Long visitors = statViewParam.getVisitors();

        try {
            if (visitors == null) {
                writeStats(username, posts, tags);
            } else {
                writeStatsWithVisitors(username, posts, tags, visitors);
            }
            return IOUtils.toByteArray(new FileInputStream(String.format(POST_STAT_VIEW_MADE_IMAGE_FILE_PATH, username)));
        } catch (FileNotFoundException fileNotFoundException) {
            throw new PostStatViewException(VIEW_BACKGROUND_NOT_FOUND);
        } catch (IOException ioException) {
            throw new PostStatViewException(VIEW_MADE_IMAGE_LOAD_FAILURE);
        }
    }

    public void writeStatsWithVisitors(String username, int posts, List<String> tags, Long visitors) {
        try {
            BufferedImage image = ImageIO.read(new File(POST_STAT_VIEW_BACKGROUND_IMAGE_FILE_PATH));

            graphics = image.getGraphics();
            graphics.setColor(Color.WHITE);

            writeUsername(username);
            writeTags(tags);
            writeVisitors(visitors);
            writePosts(posts);

            // 수정된 사진 새로 저장
            ImageIO.write(image, "png", new File(String.format(POST_STAT_VIEW_MADE_IMAGE_FILE_PATH, username)));
            graphics.dispose();
        }catch (IOException exception) {
            throw new PostStatViewException(VIEW_MADE_IMAGE_LOAD_FAILURE);
        }
    }

    public void writeStats(String username, int posts, List<String> tags) {
        try {
            BufferedImage image = ImageIO.read(new File(POST_STAT_VIEW_BACKGROUND_IMAGE_FILE_PATH));

            graphics = image.getGraphics();
            graphics.setColor(Color.WHITE);

            writeUsername(username);
            writeTags(tags);
            writePosts(posts);

            // 수정된 사진 새로 저장
            ImageIO.write(image, "png", new File(String.format(POST_STAT_VIEW_MADE_IMAGE_FILE_PATH, username)));
            graphics.dispose();
        } catch (IOException exception) {
            throw new PostStatViewException(VIEW_MADE_IMAGE_LOAD_FAILURE);
        }
    }

    private void writeUsername(String username) {
        graphics.setFont(new Font("NanumGothic", Font.BOLD, 17));
        graphics.drawString(username + POST_STAT_VIEW_SUBJECT_SUFFIX, 85, 50);
    }

    private void writeVisitors(Long visitors) {
        graphics.setFont(new Font("NanumGothic", Font.PLAIN, 15));
        graphics.drawString(POST_STAT_VIEW_VISITORS_PREFIX + visitors, 30, 140);
    }

    private void writePosts(int posts) {
        graphics.setFont(new Font("NanumGothic", Font.PLAIN, 15));
        graphics.drawString(POST_STAT_VIEW_POSTS_PREFIX + posts, 30, 80);
    }

    private void writeTags(List<String> tags) {
        String tagSentence = String.join(", ", tags);
        graphics.setFont(new Font("NanumGothic", Font.PLAIN, 15));
        graphics.drawString(POST_STAT_VIEW_TAGS_PREFIX + tagSentence, 30, 110);
    }
}
