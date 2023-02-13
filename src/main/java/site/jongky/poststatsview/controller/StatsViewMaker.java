package site.jongky.poststatsview.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Component
public class StatsViewMaker {
    private final String POST_STATS_VIEW_BACKGROUND_IMAGE_FILE_PATH = "statviewimages/post-stats-view-background.png";
    private final String POST_STATS_VIEW_MADE_IMAGE_FILE_PATH = "statviewimages/user-post-stats-view.png";

    private final String POST_STATS_VIEW_SUBJECT_SUFFIX = "\'s velog Post Stats View";
    private final String POST_STATS_VIEW_TOTAL_READS_PREFIX = "Total Reads : ";

    public byte[] makeStatsView(String username, Long totalReads) throws IOException {
        addUsername(username);
        addTotalReads(totalReads);
        return IOUtils.toByteArray(new FileInputStream(POST_STATS_VIEW_MADE_IMAGE_FILE_PATH));
    }

    public void addUsername(String username) throws IOException {
        BufferedImage image = ImageIO.read( new File(POST_STATS_VIEW_BACKGROUND_IMAGE_FILE_PATH));

        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("NanumGothic", Font.BOLD, 17));
        // username 삽입
        graphics.drawString(username + POST_STATS_VIEW_SUBJECT_SUFFIX, 85, 50);
        // 수정된 사진 새로 저장
        ImageIO.write(image, "png", new File(POST_STATS_VIEW_MADE_IMAGE_FILE_PATH)); // 문자열이 삽입된 PNG 파일 저장
        graphics.dispose();
    }

    public void addTotalReads(Long totalReads) throws IOException {
        BufferedImage image = ImageIO.read( new File(POST_STATS_VIEW_MADE_IMAGE_FILE_PATH));

        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("NanumGothic", Font.PLAIN, 15));
        // username 삽입
        graphics.drawString(POST_STATS_VIEW_TOTAL_READS_PREFIX + totalReads, 30, 80);
        // 수정된 사진 새로 저장
        ImageIO.write(image, "png", new File(POST_STATS_VIEW_MADE_IMAGE_FILE_PATH)); // 문자열이 삽입된 PNG 파일 저장
        graphics.dispose();
    }
}
