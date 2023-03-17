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
        graphics.setFont(new Font("맑은고딕", Font.BOLD, 19));

        graphics.setColor(new Color(226,240,217));
        graphics.drawString(username, 20, 40);
        int usernameWidth = graphics.getFontMetrics().stringWidth(username);

        graphics.setColor(Color.WHITE);
        graphics.drawString("'s velog Stats", 20 + usernameWidth, 40);
    }

    private void writePosts(int posts) {
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("맑은고딕", Font.BOLD, 15));
        graphics.drawString("Total Posts :", 15, 80);

        graphics.setColor(new Color(226,240,217));
        graphics.drawString(String.valueOf(posts), 170, 80);
    }

    private void writeTags(List<String> tags) {
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("맑은고딕", Font.BOLD, 15));
        graphics.drawString("Tags (most 3) :", 15, 120);

        String tagSentence = String.join(" / ", tags);
        graphics.setColor(new Color(226,240,217));
        graphics.drawString(tagSentence, 170, 120);
    }

    private void writeVisitors(Long visitors) {
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("맑은고딕", Font.BOLD, 15));
        graphics.drawString("Total Visitors :", 15, 160);

        graphics.setColor(new Color(226,240,217));
        graphics.drawString(String.valueOf(visitors), 170, 160);
    }
}
