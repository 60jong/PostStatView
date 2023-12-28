package site._60jong.poststatview.util;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class StatViewUtil {

    public static double getTextLength(String text, Font font) {

        AffineTransform transform = font.getTransform();
        FontRenderContext context = new FontRenderContext(transform, false, false);
        Rectangle2D stringBounds = font.getStringBounds(text, context);

        return stringBounds.getWidth();
    }
}
