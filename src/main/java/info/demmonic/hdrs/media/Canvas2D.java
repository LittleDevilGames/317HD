package info.demmonic.hdrs.media;

import info.demmonic.hdrs.node.CacheLink;

import java.util.Arrays;

public class Canvas2D extends CacheLink {

    public static int alpha;
    public static int width;
    public static int height;
    public static int center_y;
    public static int center_x;
    public static int bound;
    public static int leftX;
    public static int leftY;
    public static int rightX;
    public static int rightY;
    public static int pixels[];

    public static void clear() {
        Arrays.fill(Canvas2D.pixels, 0);
    }

    public static void draw(byte mask[], int maskOff, int destOff, int width, int height, int destStep, int maskStep, int color) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (mask[maskOff++] != 0) {
                    Canvas2D.pixels[destOff++] = color;
                } else {
                    destOff++;
                }
            }
            destOff += destStep;
            maskOff += maskStep;
        }
    }

    public static void draw(byte mask[], int maskOff, int destOff, int width, int height, int destStep, int maskStep, int color, int opacity) {
        color = ((color & 0xff00ff) * opacity & 0xff00ff00) + ((color & 0xff00) * opacity & 0xff0000) >> 8;
        opacity = 256 - opacity;
        for (int y = -height; y < 0; y++) {
            for (int x = -width; x < 0; x++) {
                if (mask[maskOff++] != 0) {
                    int rgb = Canvas2D.pixels[destOff];
                    Canvas2D.pixels[destOff++] = (((rgb & 0xff00ff) * opacity & 0xff00ff00) + ((rgb & 0xff00) * opacity & 0xff0000) >> 8) + color;
                } else {
                    destOff++;
                }
            }
            destOff += destStep;
            maskOff += maskStep;
        }
    }

    public static void draw(byte[] src, int[] palette, int srcOff, int destOff, int width, int height, int destStep, int srcStep) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                byte color = src[srcOff++];
                if (color != 0) {
                    Canvas2D.pixels[destOff++] = palette[color & 0xFF];
                } else {
                    destOff++;
                }
            }

            destOff += destStep;
            srcOff += srcStep;
        }
    }

    public static void draw(int src[], byte mask[], int srcOff, int maskOff, int width, int height, int destStep, int srcStep) {
        int color = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                color = src[srcOff++];
                if (color != 0 && mask[maskOff] == 0) {
                    Canvas2D.pixels[maskOff++] = color;
                } else {
                    maskOff++;
                }
            }
            maskOff += destStep;
            srcOff += srcStep;
        }
    }

    public static void draw(int src[], int srcOff, int destOff, int width, int height, int destStep, int srcStep, DrawType type) {
        int rgb = 0;
        switch (type) {
            case RGB: {
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        Canvas2D.pixels[destOff++] = src[srcOff++];
                    }
                    destOff += destStep;
                    srcOff += srcStep;
                }
                break;
            }
            case TRANSLUCENT_IGNORE_BLACK: {
                int a = 256 - alpha;
                for (int y = -height; y < 0; y++) {
                    for (int x = -width; x < 0; x++) {
                        rgb = src[srcOff++];
                        if (rgb != 0) {
                            int originalRGB = Canvas2D.pixels[destOff];
                            Canvas2D.pixels[destOff++] = ((rgb & 0xff00ff) * alpha + (originalRGB & 0xff00ff) * a & 0xff00ff00) + ((rgb & 0xff00) * alpha + (originalRGB & 0xff00) * a & 0xff0000) >> 8;
                        } else {
                            destOff++;
                        }
                    }
                    destOff += destStep;
                    srcOff += srcStep;
                }
                break;
            }
            case IGNORE_BLACK: {
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        rgb = src[srcOff++];
                        if (rgb != 0) {
                            Canvas2D.pixels[destOff++] = rgb;
                        } else {
                            destOff++;
                        }
                    }

                    destOff += destStep;
                    srcOff += srcStep;
                }
                break;
            }
            default: {
            }
        }
    }

    public static void drawLine(int x0, int y0, int x1, int y1, int color) {
        if (x0 < 0) {
            x0 = 0;
        }
        if (y0 < 0) {
            y0 = 0;
        }
        if (x1 > Canvas2D.width) {
            x1 = Canvas2D.width;
        }
        if (y1 > Canvas2D.height) {
            y1 = Canvas2D.height;
        }
        int x_difference = Math.abs(x1 - x0);
        int y_difference = Math.abs(y1 - y0);
        int x_delta = (x0 < x1 ? 1 : -1);
        int y_delta = (y0 < y1 ? 1 : -1);
        int slope = x_difference - y_difference;
        int err2 = 0;

        for (; ; ) {
            Canvas2D.plot(x0, y0, color);

            if (x0 == x1 && y0 == y1) {
                break;
            }

            err2 = 2 * slope;

            if (err2 > -y_difference) {
                slope -= y_difference;
                x0 += x_delta;
            }

            if (x0 == x1 && y0 == y1) {
                Canvas2D.plot(x0, y0, color);
                break;
            }

            if (err2 < x_difference) {
                slope += x_difference;
                y0 += y_delta;
            }
        }
    }

    public static void drawLineH(int x, int y, int len, int rgb) {
        if (y < Canvas2D.leftY || y >= Canvas2D.rightY) {
            return;
        }
        if (x < Canvas2D.leftX) {
            len -= Canvas2D.leftX - x;
            x = Canvas2D.leftX;
        }
        if (x + len > Canvas2D.rightX) {
            len = Canvas2D.rightX - x;
        }
        int pos = x + y * Canvas2D.width;
        for (int i = 0; i < len; i++) {
            Canvas2D.pixels[pos + i] = rgb;
        }
    }

    public static void drawLineH(int x, int y, int length, int color, int opacity) {
        if (y < Canvas2D.leftY || y >= Canvas2D.rightY) {
            return;
        }
        if (x < Canvas2D.leftX) {
            length -= Canvas2D.leftX - x;
            x = Canvas2D.leftX;
        }
        if (x + length > Canvas2D.rightX) {
            length = Canvas2D.rightX - x;
        }
        int alpha = 256 - opacity;
        int red = (color >> 16 & 0xff) * opacity;
        int green = (color >> 8 & 0xff) * opacity;
        int blue = (color & 0xff) * opacity;
        int position = x + y * width;
        for (int i = 0; i < length; i++) {
            Canvas2D.pixels[position] = Canvas2D.mix(red, green, blue, pixels[position++], alpha);
        }

    }

    public static void drawLineV(int x, int y, int length, int rgb) {
        if (x < Canvas2D.leftX || x >= Canvas2D.rightX) {
            return;
        }
        if (y < Canvas2D.leftY) {
            length -= Canvas2D.leftY - y;
            y = Canvas2D.leftY;
        }
        if (y + length > Canvas2D.rightY) {
            length = Canvas2D.rightY - y;
        }
        int position = x + y * Canvas2D.width;
        for (int i = 0; i < length; i++) {
            Canvas2D.pixels[position + i * Canvas2D.width] = rgb;
        }
    }

    public static void drawLineV(int x, int y, int length, int color, int opacity) {
        if (x < leftX || x >= rightX) {
            return;
        }
        if (y < leftY) {
            length -= leftY - y;
            y = leftY;
        }
        if (y + length > rightY) {
            length = rightY - y;
        }
        int alpha = 256 - opacity;
        int red = (color >> 16 & 0xff) * opacity;
        int green = (color >> 8 & 0xff) * opacity;
        int blue = (color & 0xff) * opacity;
        int position = x + y * width;
        for (int i = 0; i < length; i++) {
            Canvas2D.pixels[position] = Canvas2D.mix(red, green, blue, pixels[position], alpha);
            position += Canvas2D.width;
        }
    }

    public static void drawRect(int x, int y, int width, int height, int color) {
        drawLineH(x, y, width, color);
        drawLineH(x, (y + height) - 1, width, color);
        drawLineV(x, y, height, color);
        drawLineV((x + width) - 1, y, height, color);
    }

    public static void drawRect(int x, int y, int width, int height, int color, int opacity) {
        drawLineH(x, y, width, color, opacity);
        drawLineH(x, (y + height) - 1, width, color, opacity);
        if (height >= 3) {
            drawLineV(x, y + 1, height - 2, color, opacity);
            drawLineV((x + width) - 1, y + 1, height - 2, color, opacity);
        }
    }

    public static void fillRect(int x, int y, int width, int height, int color) {
        if (x < Canvas2D.leftX) {
            width -= Canvas2D.leftX - x;
            x = Canvas2D.leftX;
        }
        if (y < Canvas2D.leftY) {
            height -= Canvas2D.leftY - y;
            y = Canvas2D.leftY;
        }
        if (x + width > Canvas2D.rightX) {
            width = Canvas2D.rightX - x;
        }
        if (y + height > Canvas2D.rightY) {
            height = Canvas2D.rightY - y;
        }
        int step = Canvas2D.width - width;
        int position = x + y * Canvas2D.width;
        for (int i = -height; i < 0; i++) {
            for (int j = -width; j < 0; j++) {
                Canvas2D.pixels[position++] = color;
            }
            position += step;
        }
    }

    public static void fillRect(int x, int y, int width, int height, int color, int opacity) {
        if (x < Canvas2D.leftX) {
            width -= Canvas2D.leftX - x;
            x = Canvas2D.leftX;
        }
        if (y < Canvas2D.leftY) {
            height -= Canvas2D.leftY - y;
            y = Canvas2D.leftY;
        }
        if (x + width > Canvas2D.rightX) {
            width = Canvas2D.rightX - x;
        }
        if (y + height > Canvas2D.rightY) {
            height = Canvas2D.rightY - y;
        }
        int alpha = 256 - opacity;
        int red = (color >> 16 & 0xff) * opacity;
        int green = (color >> 8 & 0xff) * opacity;
        int blue = (color & 0xff) * opacity;
        int step = Canvas2D.width - width;
        int position = x + y * Canvas2D.width;
        for (int i = 0; i < height; i++) {
            for (int j = -width; j < 0; j++) {
                Canvas2D.pixels[position] = Canvas2D.mix(red, green, blue, pixels[position++], alpha);
            }
            position += step;
        }
    }

    public static String getString() {
        return "[Raster: " + width + ", " + height + ']';
    }

    public static int mix(int red, int green, int blue, int color, int alpha) {
        int r = (color >> 16 & 0xFF) * alpha;
        int g = (color >> 8 & 0xFF) * alpha;
        int b = (color & 0xFF) * alpha;
        return ((red + r >> 8) << 16) + ((green + g >> 8) << 8) + (blue + b >> 8);
    }

    public static void plot(int x, int y, int color) {
        Canvas2D.pixels[x + (y * Canvas2D.width)] = color;
    }

    public static void prepare(int width, int height, int[] pixels) {
        Canvas2D.pixels = pixels;
        Canvas2D.width = width;
        Canvas2D.height = height;
        Canvas2D.setBounds(0, 0, width, height);
    }

    public static void reset() {
        Canvas2D.leftX = 0;
        Canvas2D.leftY = 0;
        Canvas2D.rightX = width;
        Canvas2D.rightY = height;
        Canvas2D.bound = rightX - 1;
        Canvas2D.center_x = rightX / 2;
        Canvas2D.center_y = rightY / 2;
    }

    public static void setBounds(int x0, int y0, int x1, int y1) {
        if (x0 < 0) {
            x0 = 0;
        }

        if (y0 < 0) {
            y0 = 0;
        }

        if (x1 > Canvas2D.width) {
            x1 = Canvas2D.width;
        }

        if (y1 > Canvas2D.height) {
            y1 = Canvas2D.height;
        }

        Canvas2D.leftX = x0;
        Canvas2D.leftY = y0;
        Canvas2D.rightX = x1;
        Canvas2D.rightY = y1;
        Canvas2D.bound = rightX - 1;
        Canvas2D.center_x = rightX / 2;
        Canvas2D.center_y = rightY / 2;
    }

    public enum DrawType {
        IGNORE_BLACK, RGB, RGBA, TRANSLUCENT_IGNORE_BLACK
    }

}
