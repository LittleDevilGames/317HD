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
        int dst_off = x + y * Canvas2D.width;
        int dst_step = Canvas2D.width - width;
        int msk_step = 0;
        int msk_off = 0;

        if (y < left_y) {
            int i = left_y - y;
            height -= i;
            y = left_y;
            msk_off += i * width;
            dst_off += i * Canvas2D.width;
        }

        if (y + height >= right_y) {
            height -= ((y + height) - right_y) + 1;
        }

        if (x < left_x) {
            int i = left_x - x;
            width -= i;
            x = left_x;
            msk_off += i;
            dst_off += i;
            msk_step += i;
            dst_step += i;
        }

        if (x + width >= right_x) {
            int i = ((x + width) - right_x) + 1;
            width -= i;
            msk_step += i;
            dst_step += i;
        }

        if (width <= 0 || height <= 0) {
            return;
        }

        draw(mask, msk_off, dst_off, width, height, dst_step, msk_step, color);
    }

    public void drawChar(int opacity, int x, byte mask[], int width, int y, int height, boolean flag, int color) {
        int destOff = x + y * Canvas2D.width;
        int destStep = Canvas2D.width - width;
        int maskStep = 0;
        int maskOff = 0;
        if (y < left_y) {
            int yStep = left_y - y;
            height -= yStep;
            y = left_y;
            maskOff += yStep * width;
            destOff += yStep * Canvas2D.width;
        }
        if (y + height >= right_y) {
            height -= ((y + height) - right_y) + 1;
        }
        if (x < left_x) {
            int xStep = left_x - x;
            width -= xStep;
            x = left_x;
            maskOff += xStep;
            destOff += xStep;
            maskStep += xStep;
            destStep += xStep;
        }
        if (x + width >= right_x) {
            int step = ((x + width) - right_x) + 1;
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
