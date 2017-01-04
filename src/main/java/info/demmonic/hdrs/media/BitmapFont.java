package info.demmonic.hdrs.media;

import info.demmonic.hdrs.cache.Archive;
import info.demmonic.hdrs.io.Buffer;
import info.demmonic.hdrs.util.BitUtils;
import info.demmonic.hdrs.util.RSColor;

public class BitmapFont extends Canvas2D {

    public static final byte CENTER = (byte) 0x1;
    public static final byte RIGHT = (byte) 0x2;
    public static final byte SHADOW = (byte) 0x4;
    public static final byte ALLOW_TAGS = (byte) 0x8;
    public static final byte SHADOW_CENTER = SHADOW | CENTER;

    public static BitmapFont SMALL, NORMAL, BOLD, FANCY;
    public byte[] charWidth;
    public int height;
    public byte[][] mask;
    public byte[] maskHeight;
    public byte[] maskWidth;
    public byte[] offsetX;
    public byte[] offsetY;
    public boolean strikethrough;

    public BitmapFont(String name, Archive archive) {
        this.mask = new byte[256][];
        this.maskWidth = new byte[256];
        this.maskHeight = new byte[256];
        this.offsetX = new byte[256];
        this.offsetY = new byte[256];
        this.charWidth = new byte[256];
        this.strikethrough = false;

        Buffer data = new Buffer(archive.get(name + ".dat"));
        Buffer idx = new Buffer(archive.get("index.dat"));
        idx.position = data.readUnsignedShort() + 4;

        int i = idx.readUnsignedByte();

        if (i > 0) {
            idx.position += 3 * (i - 1);
        }

        for (i = 0; i < 256; i++) {
            this.offsetX[i] = idx.readByte();
            this.offsetY[i] = idx.readByte();
            int width = this.maskWidth[i] = (byte) idx.readUnsignedShort();
            int height = this.maskHeight[i] = (byte) idx.readUnsignedShort();
            int type = idx.readUnsignedByte();
            this.mask[i] = new byte[width * height];

            if (type == 0) {
                for (int j = 0; j < this.mask[i].length; j++) {
                    this.mask[i][j] = data.readByte();
                }
            } else if (type == 1) {
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        mask[i][x + y * width] = data.readByte();
                    }
                }
            }

            if (height > this.height && i < 128) {
                this.height = height;
            }

            this.offsetX[i] = 1;
            this.charWidth[i] = (byte) (width + 2);

            int k2 = 0;
            for (int y = height / 7; y < height; y++) {
                k2 += this.mask[i][y * width];
            }

            if (k2 <= height / 7) {
                this.charWidth[i]--;
                this.offsetX[i] = 0;
            }

            k2 = 0;
            for (int y = height / 7; y < height; y++) {
                k2 += this.mask[i][(width - 1) + y * width];
            }

            if (k2 <= height / 7) {
                this.charWidth[i]--;
            }

        }

        if (name.equals("q8_full")) {
            this.charWidth[32] = this.charWidth[73];
            return;
        } else {
            this.charWidth[32] = this.charWidth[105];
            return;
        }
    }

    public void draw(int color, String s, int x, int y) {
        if (s == null) {
            return;
        }
        y -= height;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != ' ') {
                drawChar(mask[c], x + offsetX[c], y + offsetY[c], maskWidth[c], maskHeight[c], color);
            }
            x += charWidth[c];
        }
    }

    public void draw(int color, String s, int x, int k, int y) {
        if (s == null) {
            return;
        }
        x -= getWidth(s) / 2;
        y -= height;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != ' ') {
                drawChar(mask[c], x + offsetX[c], y + offsetY[c] + (int) (Math.sin((double) i / 2D + (double) k / 5D) * 5D), maskWidth[c], maskHeight[c], color);
            }
            x += charWidth[c];
        }

    }

    public void draw(String s, int x, int y, int color) {
        draw(s, x, y, color, 0);
    }

    public void draw(String s, int x, int y, int color, int flags) {
        if (s == null) {
            return;
        }

        if (BitUtils.flagged(flags, CENTER)) {
            x -= getWidth(s) / 2;
        }
        if (BitUtils.flagged(flags, RIGHT)) {
            x -= getWidth(s);
        }

        this.drawString(s, x, y, color, BitUtils.flagged(flags, SHADOW), BitUtils.flagged(flags, ALLOW_TAGS));
    }

    public void drawCenteredWavy(int amplitude, String s, boolean flag, int loop, int y, int x, int color) {
        if (s == null) {
            return;
        }

        double offset = 7D - (double) amplitude / 8D;

        if (offset < 0.0D) {
            offset = 0.0D;
        }

        x -= getWidth(s) / 2;
        y -= height;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != ' ') {
                drawChar(mask[c], x + offsetX[c], y + offsetY[c] + (int) (Math.sin((double) i / 1.5D + (double) loop) * offset), maskWidth[c], maskHeight[c], color);
            }
            x += charWidth[c];
        }

    }

    public void drawCenteredWavy2(int x, String s, int cycle, int y, int l) {
        if (s == null) {
            return;
        }

        x -= getWidth(s) / 2;
        y -= height;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != ' ') {
                drawChar(mask[c], x + offsetX[c] + (int) (Math.sin((double) i / 5D + (double) cycle / 5D) * 5D), y + offsetY[c] + (int) (Math.sin((double) i / 3D + (double) cycle / 5D) * 5D), maskWidth[c], maskHeight[c], l);
            }
            x += charWidth[c];
        }

    }

    public void drawChar(byte mask[], int x, int y, int width, int height, int color) {
        int dstOff = 0;
        int dstStep = 0;
        int mskStep = 0;
        int mskOff = 0;

/*        if (y < leftY) {
            int i = leftY - y;
            height -= i;
            y = leftY;
            mskOff += i * width;
            dstOff += i * Canvas2D.width;
        }

        if (y + height >= rightY) {
            height -= ((y + height) - rightY) + 1;
        }

        if (x < leftX) {
            int i = leftX - x;
            width -= i;
            x = leftX;
            mskOff += i;
            dstOff += i;
            mskStep += i;
            dstStep += i;
        }

        if (x + width >= rightX) {
            int i = ((x + width) - rightX) + 1;
            width -= i;
            mskStep += i;
            dstStep += i;
        }

        if (width <= 0 || height <= 0) {
            return;
        }*/

        draw(mask, mskOff, dstOff, width, height, dstStep, mskStep, x, y, color);
    }

    public void drawChar(int opacity, int x, byte mask[], int width, int y, int height, boolean flag, int color) {
        int destOff = x + y * Canvas2D.width;
        int destStep = Canvas2D.width - width;
        int maskStep = 0;
        int maskOff = 0;
        if (y < leftY) {
            int yStep = leftY - y;
            height -= yStep;
            y = leftY;
            maskOff += yStep * width;
            destOff += yStep * Canvas2D.width;
        }
        if (y + height >= rightY) {
            height -= ((y + height) - rightY) + 1;
        }
        if (x < leftX) {
            int xStep = leftX - x;
            width -= xStep;
            x = leftX;
            maskOff += xStep;
            destOff += xStep;
            maskStep += xStep;
            destStep += xStep;
        }
        if (x + width >= rightX) {
            int step = ((x + width) - rightX) + 1;
            width -= step;
            maskStep += step;
            destStep += step;
        }
        if (width <= 0 || height <= 0) {
            return;
        }

        draw(mask, maskOff, destOff, width, height, destStep, maskStep, color, opacity);
    }

    public void drawString(boolean shadow, int x, int color, String s, int y, int opacity) {
        if (s == null) {
            return;
        }
        y -= height;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '@' && i + 4 < s.length() && s.charAt(i + 4) == '@') {
                int tagColor = getTagColor(s.substring(i + 1, i + 4));
                if (tagColor != -1) {
                    color = tagColor;
                }
                i += 4;
            } else {
                char c = s.charAt(i);
                if (c != ' ') {
                    if (shadow) {
                        drawChar(opacity / 2, x + offsetX[c] + 1, mask[c], maskWidth[c], y + offsetY[c] + 1, maskHeight[c], false, 0);
                    }
                    drawChar(opacity, x + offsetX[c], mask[c], maskWidth[c], y + offsetY[c], maskHeight[c], false, color);
                }
                x += charWidth[c];
            }
        }

    }

    public void drawString(String s, int x, int y, int color, boolean shadow, boolean allowTags) {
        strikethrough = false;
        int startX = x;
        if (s == null) {
            return;
        }
        y -= height;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '@' && i + 4 < s.length() && s.charAt(i + 4) == '@' && allowTags) {
                int rgb = getTagColor(s.substring(i + 1, i + 4));
                if (rgb != -1) {
                    color = rgb;
                }
                i += 4;
            } else {
                char c = s.charAt(i);
                if (c != ' ') {
                    if (shadow) {
                        drawChar(mask[c], x + offsetX[c] + 1, y + offsetY[c] + 1, maskWidth[c], maskHeight[c], 0);
                    }
                    drawChar(mask[c], x + offsetX[c], y + offsetY[c], maskWidth[c], maskHeight[c], color);
                }
                x += charWidth[c];
            }
        }
        if (strikethrough) {
            drawLineH(startX, y + (int) ((double) height * 0.7D), x - startX, 0x800000);
        }
    }

    public int getTagColor(String s) {
        if (s.equals("str")) {
            strikethrough = true;
            return -1;
        } else if (s.equals("end")) {
            strikethrough = false;
            return -1;
        }

        return RSColor.getTagColor(s);
    }

    public int getWidth(String s) {
        if (s == null) {
            return 0;
        }
        int width = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '@' && i + 4 < s.length() && s.charAt(i + 4) == '@') {
                i += 4;
            } else {
                width += charWidth[s.charAt(i)];
            }
        }
        return width;
    }
}
