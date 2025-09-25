package site._60jong.poststatview.service.velog.view;

import java.awt.Font;

public final class SvgConstants {

    // Fonts
    public static final String FONT_FAMILY = "arial";
    public static final Font FONT_DEFAULT = new Font(FONT_FAMILY, Font.PLAIN, 14);
    public static final Font FONT_TAG = new Font(FONT_FAMILY, Font.PLAIN, 12);
    public static final int FONT_SIZE_DEFAULT = 14;
    public static final int FONT_SIZE_TAG = 12;

    // Colors
    public static final String BACKGROUND_COLOR = "#ffffff";
    public static final String TEXT_COLOR = "#000000";
    public static final String TAG_BACKGROUND_COLOR = "#eeeeee";
    public static final String TAG_TEXT_COLOR = "#20C997";

    // View Layout
    public static final int VIEW_HEIGHT = 60;
    public static final int VIEW_RECT_HEIGHT = 54;
    public static final int VIEW_BORDER_RADIUS = 5;
    public static final int LEFT_PADDING = 30;
    public static final int RIGHT_PADDING = 10;
    public static final int SECTION_GAP = 50;

    // Text Layout
    public static final double TEXT_Y = 18.5;
    public static final int ICON_OFFSET = 20;

    // Tag Layout
    public static final int TAG_START_X = 30;
    public static final int TAG_BOX_Y = 28;
    public static final int TAG_TEXT_Y = 42;
    public static final int TAG_BOX_HEIGHT = 20;
    public static final int TAG_BORDER_RADIUS = 7;
    public static final int TAG_PADDING_HORIZONTAL = 8;
    public static final int TAG_GAP = 12;

    // SVG Icons (Path data)
    public static final String VELOG_ICON = """
            <path d="M 18.6199 8.526V7.54163C17.9949 7.3385 17.2605 7.11975 16.4167 6.88538C15.573 6.63538 15.0027 6.51038 14.7058 6.51038C14.0496 6.51038 13.6589 6.82288 13.5339 7.44788L12.0105 16.0963C11.5261 15.4557 11.1277 14.9166 10.8152 14.4791C10.3308 13.7916 9.8855 13.0026 9.47925 12.1119C9.05737 11.2213 8.84644 10.4244 8.84644 9.72131C8.84644 9.29944 8.96362 8.9635 9.198 8.7135C9.41675 8.44788 9.83081 8.11194 10.4402 7.70569C9.81519 6.90881 9.03393 6.51038 8.09643 6.51038C7.59644 6.51038 7.18237 6.65881 6.85425 6.95569C6.5105 7.25256 6.33862 7.69006 6.33862 8.26819C6.33862 9.23694 6.74487 10.4479 7.55737 11.901C8.35425 13.3385 9.89331 15.5026 12.1746 18.3932L14.4949 18.5573L16.2761 8.526H18.6199Z" fill="#20C997"/>
            <svg x="8" y="32" width="12px" height="12px" viewBox="0 0 16 16" fill="none"> 
              <path fill="#20C997" fill-rule="evenodd" d="M6.884 1.762a.75.75 0 01.604.872L7.058 5h2.975l.479-2.634a.75.75 0 111.476.268L11.558 5h1.192a.75.75 0 010 1.5h-1.465l-.546 3h2.011a.75.75 0 010 1.5h-2.283l-.48 2.634a.75.75 0 11-1.475-.268L8.942 11H5.967l-.48 2.634a.75.75 0 11-1.475-.268L4.442 11H3.25a.75.75 0 010-1.5h1.465l.545-3H3.25a.75.75 0 010-1.5h2.283l.479-2.634a.75.75 0 01.872-.604zM9.214 9.5l.546-3H6.785l-.546 3h2.976z" clip-rule="evenodd"/> 
            </svg>
            """;
    public static final String POST_ICON = """
            <svg x="%.2f" y="5" width="16" height="16" viewBox="0 0 24 24" fill="none">
              <path d="M15 8H17M15 12H17M17 16H7M7 8V12H11V8H7ZM5 20H19C20.1046 20 21 19.1046 21 18V6C21 4.89543 20.1046 4 19 4H5C3.89543 4 3 4.89543 3 6V18C3 19.1046 3.89543 20 5 20Z" stroke="#20c997" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
            </svg>
            """;
    public static final String VISITOR_ICON = """
            <svg x="%.2f" y="5" width="16px" height="16px" viewBox="0 -0.778 14 14" fill="#20c997">
                <path d="M7 3.5a2.696 2.696 0 0 0 -0.759 0.122 1.347 1.347 0 0 1 0.176 0.656 1.361 1.361 0 0 1 -1.361 1.361 1.347 1.347 0 0 1 -0.656 -0.176A2.715 2.715 0 1 0 7 3.5zm6.915 2.367C12.597 3.296 9.988 1.556 7 1.556S1.402 3.297 0.085 5.868a0.786 0.786 0 0 0 0 0.709C1.403 9.149 4.012 10.889 7 10.889s5.598 -1.741 6.915 -4.312a0.786 0.786 0 0 0 0 -0.709zM7 9.722c-2.398 0 -4.596 -1.337 -5.783 -3.5C2.404 4.059 4.602 2.722 7 2.722s4.596 1.337 5.783 3.5C11.596 8.385 9.398 9.722 7 9.722z"/>
            </svg>
            """;

    private SvgConstants() {}
}
