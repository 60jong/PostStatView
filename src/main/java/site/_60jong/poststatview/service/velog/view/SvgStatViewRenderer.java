package site._60jong.poststatview.service.velog.view;

import org.springframework.stereotype.Component;
import site._60jong.poststatview.util.StatViewUtil;

import java.awt.Font;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static site._60jong.poststatview.service.velog.view.SvgConstants.*;

@Component
public class SvgStatViewRenderer {

    public String render(VelogStatViewParam param) {
        // 1. 레이아웃 계산
        LayoutMetrics metrics = calculateLayoutMetrics(param);
        String tagsSvg = renderTags(param.getTagNames(), metrics);

        // 2. SVG 템플릿에 데이터 삽입
        return """
                <svg width="%.2f" height="%d" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <style>
                        .name { fill: %s; font-weight: 500; font-size: %dpx; font-family: '%s'; }
                        .stat { font-size: %dpx; }
                        .tag { fill: %s; font-weight: 500; font-size: %dpx; font-family: '%s'; }
                    </style>
                    <g>
                        <rect x="0" rx="%d" width="%.2f" height="%d" fill="%s"/>
                        <g>
                            %s
                            %s
                            %s
                
                            <text x="%.2f" y="%.2f" class="name">%s</text>
                            <text x="%.2f" y="%.2f" class="name stat">%d</text>
                            <text x="%.2f" y="%.2f" class="name stat">%d</text>
                
                            %s
                        </g>
                    </g>
                </svg>
                """.formatted(
                metrics.viewWidth(),
                VIEW_HEIGHT,
                TEXT_COLOR,
                FONT_SIZE_DEFAULT,
                FONT_FAMILY,
                FONT_SIZE_DEFAULT,
                TAG_TEXT_COLOR,
                FONT_SIZE_TAG,
                FONT_FAMILY,
                VIEW_BORDER_RADIUS,
                metrics.viewWidth(),
                VIEW_RECT_HEIGHT,
                BACKGROUND_COLOR,
                VELOG_ICON,
                POST_ICON.formatted(metrics.postsLocationX() - ICON_OFFSET),
                VISITOR_ICON.formatted(metrics.visitorsLocationX() - ICON_OFFSET),
                metrics.usernameLocationX(),
                TEXT_Y,
                param.getUsername(),
                metrics.postsLocationX(),
                TEXT_Y,
                param.getPosts(),
                metrics.visitorsLocationX(),
                TEXT_Y,
                param.getVisitors(),
                tagsSvg
        );
    }

    private String renderTags(List<String> tagNames, LayoutMetrics metrics) {
        AtomicReference<Double> currentX = new AtomicReference<>((double) TAG_START_X);

        return tagNames.stream()
                .map(tag -> {
                    double tagTextWidth = StatViewUtil.getTextLength(tag, FONT_TAG);
                    double tagBoxWidth = tagTextWidth + (TAG_PADDING_HORIZONTAL * 2);

                    String tagSvg = """
                                   <g>
                                       <rect x="%.2f" y="%d" rx="%d" ry="%d" height="%d" width="%.2f" fill="%s"/>
                                       <text x="%.2f" y="%d" class="tag">%s</text>
                                   </g>
                                   """.formatted(
                            currentX.get(),
                            TAG_BOX_Y,
                            TAG_BORDER_RADIUS,
                            TAG_BORDER_RADIUS,
                            TAG_BOX_HEIGHT,
                            tagBoxWidth,
                            TAG_BACKGROUND_COLOR,
                            currentX.get() + TAG_PADDING_HORIZONTAL,
                            TAG_TEXT_Y,
                            tag
                    );

                    currentX.updateAndGet(x -> x + tagBoxWidth + TAG_GAP);
                    return tagSvg;
                })
                .collect(Collectors.joining("\n"));
    }

    private LayoutMetrics calculateLayoutMetrics(VelogStatViewParam param) {
        double usernameWidth = StatViewUtil.getTextLength(param.getUsername(), FONT_DEFAULT);
        double postsWidth = StatViewUtil.getTextLength(String.valueOf(param.getPosts()), FONT_DEFAULT);
        double visitorsWidth = StatViewUtil.getTextLength(String.valueOf(param.getVisitors()), FONT_DEFAULT);

        double usernameLocationX = LEFT_PADDING;
        double postsLocationX = usernameLocationX + usernameWidth + SECTION_GAP;
        double visitorsLocationX = postsLocationX + postsWidth + SECTION_GAP;
        double viewWidth = visitorsLocationX + visitorsWidth + RIGHT_PADDING;

        return new LayoutMetrics(
                viewWidth,
                usernameLocationX,
                postsLocationX,
                visitorsLocationX
        );
    }

    // 레이아웃 계산 결과 DTO
    private record LayoutMetrics(
            double viewWidth,
            double usernameLocationX,
            double postsLocationX,
            double visitorsLocationX
    ) {}
}