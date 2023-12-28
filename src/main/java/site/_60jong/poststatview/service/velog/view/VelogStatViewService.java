package site._60jong.poststatview.service.velog.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site._60jong.poststatview.service.velog.stat.VelogStatServiceV2;
import site._60jong.poststatview.service.velog.response.posts.PostId;
import site._60jong.poststatview.util.StatViewUtil;

import java.awt.*;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VelogStatViewService {

    private final VelogStatServiceV2 statService;

    public String getStatView(final String username) {

        List<PostId> postIds = statService.findAllPostId(username);
        int visitors = statService.batchFindTotalVisitorsByPostIds(postIds);

        return renderView(username, new VelogStatViewParam(postIds.size(), visitors, List.of("java", "web", "servlet")));
    }

    private String renderView(final String username, final VelogStatViewParam param) {

        Font usernameFont = new Font("default", Font.PLAIN, 14);
        Font statFont = new Font("default", Font.PLAIN, 14);
        Font tagFont = new Font("default", Font.PLAIN, 12);

        double usernameWidth = StatViewUtil.getTextLength(username, usernameFont);
        double postsWidth = StatViewUtil.getTextLength(String.valueOf(param.getPosts()), statFont);
        double visitorsWidth = StatViewUtil.getTextLength(String.valueOf(param.getVisitors()), statFont);

        double usernameLocationX = 30;
        double postsLocationX = usernameLocationX + usernameWidth + 50;
        double visitorsLocationX = postsLocationX + postsWidth + 30;
        double viewWidth = visitorsLocationX + visitorsWidth + 10;

        return  "<svg width=\"" + viewWidth + "\" height=\"60\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                "    <style>\n" +
                "        .name{ fill: black; font-weight: 500; font-size: 14px;}\n" +
                "        .stat{ font-size: 14px }\n" +
                "        .tag{ fill: #20C997; font-weight: 500; font-size: 12px; color: #20C997;}\n" +
                "    </style>\n" +
                "    <g>\n" +
                "        <rect x=\"0\" rx=\"5\" width=\"" + viewWidth + "\" height=\"54\" fill=\"#ffffff\"/>\n" +
                "        <g fill=\"#20C997\">\n" +
                "            <text x=\"30\" y=\"19\" textLength=\"" + usernameWidth + "\" class=\"name\">" + username + "</text>\n" +
                "            <text x=\"" + postsLocationX + "\" y=\"19\" textLength=\"" + postsWidth + "\" class=\"name stat\">" + param.getPosts() + "</text>\n" +
                "            <text x=\"" + visitorsLocationX + "\" y=\"19\" textLength=\"" + visitorsWidth + "\" class=\"name stat\">" + param.getVisitors() + "</text>\n" +
                "            <g>\n" +
                "                <rect x=\"22\" y=\"28\" rx=\"7\" ry=\"7\" height=\"20\" width=\"42\" fill=\"#eeeeee\"/>\n" +
                "                <text x=\"30\" y=\"42\" textLength=\"26\" class=\"tag\">java</text>\n" +
                "            </g>\n" +
                "            <g>\n" +
                "                <rect x=\"75\" y=\"28\" rx=\"7\" ry=\"7\" height=\"20\" width=\"40\" fill=\"#eeeeee\"/>\n" +
                "                <text x=\"80\" y=\"42\" textLength=\"25\" class=\"tag\">web</text>\n" +
                "            </g>\n" +
                "            <g>\n" +
                "                <rect x=\"125\" y=\"28\" rx=\"7\" ry=\"7\" height=\"20\" width=\"60\" fill=\"#eeeeee\"/>\n" +
                "                <text x=\"130\" y=\"42\" textLength=\"42\" class=\"tag\">servlet</text>\n" +
                "            </g>\n" +
                "        </g>\n" +
                "        <path d=\"M 18.6199 8.526V7.54163C17.9949 7.3385 17.2605 7.11975 16.4167 6.88538C15.573 6.63538 15.0027 6.51038 14.7058 6.51038C14.0496 6.51038 13.6589 6.82288 13.5339 7.44788L12.0105 16.0963C11.5261 15.4557 11.1277 14.9166 10.8152 14.4791C10.3308 13.7916 9.8855 13.0026 9.47925 12.1119C9.05737 11.2213 8.84644 10.4244 8.84644 9.72131C8.84644 9.29944 8.96362 8.9635 9.198 8.7135C9.41675 8.44788 9.83081 8.11194 10.4402 7.70569C9.81519 6.90881 9.03393 6.51038 8.09643 6.51038C7.59644 6.51038 7.18237 6.65881 6.85425 6.95569C6.5105 7.25256 6.33862 7.69006 6.33862 8.26819C6.33862 9.23694 6.74487 10.4479 7.55737 11.901C8.35425 13.3385 9.89331 15.5026 12.1746 18.3932L14.4949 18.5573L16.2761 8.526H18.6199Z\" fill=\"#20C997\"/>\n" +
                "    </g>\n" +
                "    <svg xmlns=\"http://www.w3.org/2000/svg\" x=\"" + (postsLocationX - 20) + "\" y=\"5\" width=\"16px\" height=\"16px\" viewBox=\"0 -0.778 14 14\" fill=\"#0d1117\">\n" +
                "        <path d=\"M7 3.5a2.696 2.696 0 0 0 -0.759 0.122 1.347 1.347 0 0 1 0.176 0.656 1.361 1.361 0 0 1 -1.361 1.361 1.347 1.347 0 0 1 -0.656 -0.176A2.715 2.715 0 1 0 7 3.5zm6.915 2.367C12.597 3.296 9.988 1.556 7 1.556S1.402 3.297 0.085 5.868a0.786 0.786 0 0 0 0 0.709C1.403 9.149 4.012 10.889 7 10.889s5.598 -1.741 6.915 -4.312a0.786 0.786 0 0 0 0 -0.709zM7 9.722c-2.398 0 -4.596 -1.337 -5.783 -3.5C2.404 4.059 4.602 2.722 7 2.722s4.596 1.337 5.783 3.5C11.596 8.385 9.398 9.722 7 9.722z\"/>\n" +
                "    </svg>\n" +
                "    <svg xmlns=\"http://www.w3.org/2000/svg\" x=\"" + (visitorsLocationX - 20) + "\" y=\"5\" width=\"16px\" height=\"16px\" viewBox=\"0 -0.778 14 14\" fill=\"#0d1117\">\n" +
                "        <path d=\"M7 3.5a2.696 2.696 0 0 0 -0.759 0.122 1.347 1.347 0 0 1 0.176 0.656 1.361 1.361 0 0 1 -1.361 1.361 1.347 1.347 0 0 1 -0.656 -0.176A2.715 2.715 0 1 0 7 3.5zm6.915 2.367C12.597 3.296 9.988 1.556 7 1.556S1.402 3.297 0.085 5.868a0.786 0.786 0 0 0 0 0.709C1.403 9.149 4.012 10.889 7 10.889s5.598 -1.741 6.915 -4.312a0.786 0.786 0 0 0 0 -0.709zM7 9.722c-2.398 0 -4.596 -1.337 -5.783 -3.5C2.404 4.059 4.602 2.722 7 2.722s4.596 1.337 5.783 3.5C11.596 8.385 9.398 9.722 7 9.722z\"/>\n" +
                "    </svg>\n" +
                "</svg>\n";
    }
}
