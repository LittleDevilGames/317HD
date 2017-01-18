package info.demmonic.hdrs.media;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import info.demmonic.hdrs.Rt3;
import info.demmonic.hdrs.cache.Archive;
import info.demmonic.hdrs.io.Buffer;
import info.demmonic.hdrs.util.BitUtils;
import info.demmonic.hdrs.util.RSColor;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class BitmapFont extends Canvas2D {

    public static final byte CENTER = (byte) 0x1;
    public static final byte RIGHT = (byte) 0x2;
    public static final byte SHADOW = (byte) 0x4;
    public static final byte ALLOW_TAGS = (byte) 0x8;
    public static final byte SHADOW_CENTER = SHADOW | CENTER;

    public static BitmapFont SMALL, NORMAL, BOLD, FANCY;
    private final Texture fontTexture;
    private int maxWidth;
    private int maxHeight;
    public byte[] charWidth;
    public int height;
    public byte[][] mask;
    public byte[] maskHeight;
    public byte[] maskWidth;
    public byte[] offsetX;
    public byte[] offsetY;
    public boolean strikethrough;
    private Table<Character, Integer, Sprite> characterSprites = HashBasedTable.create();

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

        maxWidth = 0;
        maxHeight = 0;
        for (i = 0; i < 256; i++) {
            this.offsetX[i] = idx.readByte();
            this.offsetY[i] = idx.readByte();
            int width = this.maskWidth[i] = (byte) idx.readUnsignedShort();
            int height = this.maskHeight[i] = (byte) idx.readUnsignedShort();
            int type = idx.readUnsignedByte();
            this.mask[i] = new byte[width * height];

            maxWidth = Math.max(maxWidth, width);
            maxHeight = Math.max(maxHeight, height);

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

        //loalll
        int num = 0;
        final int rows = 16;
        final int cols = 16;
        BufferedImage finalImg = new BufferedImage(maxWidth * cols, maxHeight * rows, BufferedImage.TYPE_INT_ARGB);
        BufferedImage[] fontImages = new BufferedImage[256];
        for (i = 0; i < 256; i++) {
            int width = maskWidth[i];
            int height = maskHeight[i];
            fontImages[num] = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (mask[i][y * width + x] != 0) {
                        fontImages[num].setRGB(x, y, 0xFFFFFF);
                    } else {
                        fontImages[num].setRGB(x, y, 0xFF0000);
                    }
                }
            }
            num++;
        }
        num = 0;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < cols; column++) {
                finalImg.createGraphics().drawImage(fontImages[num], maxWidth * column, maxHeight * row, null);
                num++;
            }
        }
        int[] pixels = ((DataBufferInt) finalImg.getRaster().getDataBuffer()).getData();
        Pixmap pixmap = new Pixmap(finalImg.getWidth(), finalImg.getHeight(), Pixmap.Format.RGBA8888);
        for (int y = 0; y < finalImg.getHeight(); y++) {
            for (int x = 0; x < finalImg.getWidth(); x++) {
                int color = pixels[y * finalImg.getWidth() + x];
                int r = (color & 0xFF0000) >> 16;
                int g = (color & 0xFF00) >> 8;
                int b = (color & 0xFF);
                if (color == -1) {
                    pixmap.setColor(r / 255f, g / 255f, b / 255f, 1f);
                    pixmap.drawPixel(x, y);
                }
            }
        }
        fontTexture = new Texture(pixmap);

        if (name.equals("q8_full")) {
            this.charWidth[32] = this.charWidth[73];
        } else {
            this.charWidth[32] = this.charWidth[105];
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
                drawChar(c, mask[c], x + offsetX[c], y + offsetY[c], maskWidth[c], maskHeight[c], color);
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
                drawChar(c, mask[c], x + offsetX[c], y + offsetY[c] + (int) (Math.sin((double) i / 2D + (double) k / 5D) * 5D), maskWidth[c], maskHeight[c], color);
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
                drawChar(c, mask[c], x + offsetX[c], y + offsetY[c] + (int) (Math.sin((double) i / 1.5D + (double) loop) * offset), maskWidth[c], maskHeight[c], color);
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
                drawChar(c, mask[c], x + offsetX[c] + (int) (Math.sin((double) i / 5D + (double) cycle / 5D) * 5D), y + offsetY[c] + (int) (Math.sin((double) i / 3D + (double) cycle / 5D) * 5D), maskWidth[c], maskHeight[c], l);
            }
            x += charWidth[c];
        }

    }

    public void drawChar(char character, byte mask[], int x, int y, int width, int height, int color) {
        int posX = 0;
        int posY = 0;
        int rows = 16;
        int cols = 16;
        int num = 0;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < cols; column++) {
                if (num++ == (int) character) {
                    posX = maxWidth * column;
                    posY = maxHeight * row;
                }
            }
        }
        int red = (color >> 16 & 0xff);
        int green = (color >> 8 & 0xff);
        int blue = (color & 0xff);

        Rt3.batch.setColor(new Color(red / 255f, green / 255f, blue / 255f,  1f));
        Rt3.batch.draw(fontTexture, x, y, maxWidth,  maxHeight, posX, posY, maxWidth, maxHeight, false, true);
    }

    public void drawChar(char character, int opacity, int x, byte mask[], int width, int y, int height, boolean flag, int color) {
        int destOff = 0;
        int destStep = 0;
        int maskStep = 0;
        int maskOff = 0;

        int[] pixels = new int[width * height];
        for (int y2 = 0; y2 < height; y2++) {
            for (int x2 = 0; x2 < width; x2++) {
                if (mask[maskOff++] != 0) {
                    pixels[destOff] = color;
                } else {
                    pixels[destOff] = -1;
                }
                destOff++;
            }
            destOff += destStep;
            maskOff += maskStep;
        }

        if (!characterSprites.contains(character, color)) {
            Sprite sprite = new Sprite(pixels, width, height);
            characterSprites.put(character, color, sprite);
        }

        characterSprites.get(character, color).draw(x, y, opacity);
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
                        drawChar(c, opacity / 2, x + offsetX[c] + 1, mask[c], maskWidth[c], y + offsetY[c] + 1, maskHeight[c], false, 0);
                    }
                    drawChar(c, opacity, x + offsetX[c], mask[c], maskWidth[c], y + offsetY[c], maskHeight[c], false, color);
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
                        drawChar(c, mask[c], x + offsetX[c] + 1, y + offsetY[c] + 1, maskWidth[c], maskHeight[c], 0);
                    }
                    drawChar(c, mask[c], x + offsetX[c], y + offsetY[c], maskWidth[c], maskHeight[c], color);
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
