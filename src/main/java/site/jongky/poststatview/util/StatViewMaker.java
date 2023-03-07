package site.jongky.poststatview.util;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Component
public class StatViewMaker {
    private final String POST_STATS_VIEW_BACKGROUND_IMAGE_FILE_PATH = "statviewimages/post-stat-view-background.png";
    private final String POST_STATS_VIEW_MADE_IMAGE_FILE_PATH = "statviewimages/%s-post-stat-view.png";

    private final String POST_STATS_VIEW_SUBJECT_SUFFIX = "\'s velog Post Stats View";
    private final String POST_STATS_VIEW_TOTAL_READS_PREFIX = "Total Reads : ";

    public byte[] makeStatsView(String username, Long totalReads) throws IOException {
        addStats(username, totalReads);
        return IOUtils.toByteArray(new FileInputStream(String.format(POST_STATS_VIEW_MADE_IMAGE_FILE_PATH, username)));
    }

    public void addStats(String username, Long totalReads) throws IOException {
        BufferedImage image = ImageIO.read(new File(POST_STATS_VIEW_BACKGROUND_IMAGE_FILE_PATH));

        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);

        // username 삽입
        graphics.setFont(new Font("NanumGothic", Font.BOLD, 17));
        graphics.drawString(username + POST_STATS_VIEW_SUBJECT_SUFFIX, 85, 50);

        // totalReads 삽입
        graphics.setFont(new Font("NanumGothic", Font.PLAIN, 15));
        graphics.drawString(POST_STATS_VIEW_TOTAL_READS_PREFIX + totalReads, 30, 80);

        // 수정된 사진 새로 저장
        ImageIO.write(image, "png", new File(String.format(POST_STATS_VIEW_MADE_IMAGE_FILE_PATH, username)));
        graphics.dispose();
    }
}
