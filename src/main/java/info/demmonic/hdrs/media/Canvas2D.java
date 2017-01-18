package info.demmonic.hdrs.media;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import info.demmonic.hdrs.node.CacheLink;

import java.util.Arrays;

import static info.demmonic.hdrs.Rt3.batch;

public class Canvas2D extends CacheLink {

    public static Texture rectangle;
    public static int alpha;
    public static int width;
    public static int height;
    public static int centerY2d;
    public static int centerX2d;
    public static int bound;
    public static int leftX;
    public static int leftY;
    public static int rightX;
    public static int rightY;
    public static int pixels[];

    public static void clear() {
        Arrays.fill(Canvas2D.pixels, 0);
    }

    public static void createTexture() {
        Pixmap pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                int color = 0xFFFFFF;
                int r = (color & 0xFF0000) >> 16;
                int g = (color & 0xFF00) >> 8;
                int b = (color & 0xFF);
                if (color != -1) {
                    pixmap.setColor(r / 255f, g / 255f, b / 255f, 1f);
                    pixmap.drawPixel(x, y);
                }
            }
        }
        rectangle = new Texture(pixmap);
        pixmap.dispose();
    }

    public static void draw(int src[], byte mask[], int srcOff, int maskOff, int width, int height, int destStep, int srcStep) {
/*        int color = 0;
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
        }*/
    }

    public static void drawLineH(int x, int y, int len, int rgb) {
        int red = (rgb >> 16 & 0xff);
        int green = (rgb >> 8 & 0xff);
        int blue = (rgb & 0xff);

        batch.setColor(new Color(red / 255f, green / 255f, blue / 255f, 1f));
        batch.draw(rectangle, x, y, len, 1);

    }

    public static void drawLineH(int x, int y, int length, int color, int opacity) {
        int alpha = 256 - opacity;
        int red = (color >> 16 & 0xff) * opacity;
        int green = (color >> 8 & 0xff) * opacity;
        int blue = (color & 0xff) * opacity;

        batch.setColor(new Color(red / 255f, green / 255f, blue / 255f,  alpha / 255f));
        batch.draw(rectangle, x, y, length, 1);
    }

    public static void drawLineV(int x, int y, int length, int rgb) {
        int red = (rgb >> 16 & 0xff);
        int green = (rgb >> 8 & 0xff);
        int blue = (rgb & 0xff);

        batch.setColor(new Color(red / 255f, green / 255f, blue / 255f,  1f));
        batch.draw(rectangle, x, y, 1, length);
    }

    public static void drawLineV(int x, int y, int length, int color, int opacity) {
        int alpha = 256 - opacity;
        int red = (color >> 16 & 0xff) * opacity;
        int green = (color >> 8 & 0xff) * opacity;
        int blue = (color & 0xff) * opacity;

        batch.setColor(new Color(red / 255f, green / 255f, blue / 255f,  alpha / 255f));
        batch.draw(rectangle, x, y, 1, length);
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
        int red = (color >> 16 & 0xff);
        int green = (color >> 8 & 0xff);
        int blue = (color & 0xff);

        batch.setColor(new Color(red / 255f, green / 255f, blue / 255f,  1f));
        batch.draw(rectangle, x, y, width, height);
    }

    public static void fillRect(int x, int y, int width, int height, int color, int opacity) {
        int alpha = 256 - opacity;
        int red = (color >> 16 & 0xff) * opacity;
        int green = (color >> 8 & 0xff) * opacity;
        int blue = (color & 0xff) * opacity;

        batch.setColor(new Color(red / 255f, green / 255f, blue / 255f,  alpha / 255f));
        batch.draw(rectangle, x, y, width, height);
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
        Canvas2D.centerX2d = rightX / 2;
        Canvas2D.centerY2d = rightY / 2;
    }

    public static void setBounds(int x0, int y0, int x1, int y1) {
        if (x0 < 0) {
            x0 = 0;
        }

        if (y0 < 0) {
            y0 = 0;
        }


        Canvas2D.leftX = x0;
        Canvas2D.leftY = y0;
        Canvas2D.rightX = x1;
        Canvas2D.rightY = y1;
        Canvas2D.bound = rightX - 1;
        Canvas2D.centerX2d = rightX / 2;
        Canvas2D.centerY2d = rightY / 2;
    }
}
