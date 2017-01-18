package info.demmonic.hdrs.media;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import info.demmonic.hdrs.Rt3;
import info.demmonic.hdrs.cache.Archive;
import info.demmonic.hdrs.io.Buffer;

public class Bitmap extends Canvas2D {

    public static final byte FLIP_HORIZONTALLY = 0x1;
    public static final byte FLIP_VERTICALLY = 0x2;
    public int cropHeight;
    public int cropWidth;
    public int width;
    public int height;
    public int offsetX;
    public int offsetY;
    public int[] palette;
    public byte[] pixels;
    private SpriteHelper spriteHelper;

    public Bitmap(Archive archive, String imageArchive) {
        this(archive, imageArchive, 0);
    }

    public Bitmap(Archive archive, String imageArchive, int fileIndex) {
        Buffer data = new Buffer(archive.get(imageArchive + ".dat", null));
        Buffer idx = new Buffer(archive.get("index.dat", null));

        idx.position = data.readUnsignedShort();

        this.cropWidth = idx.readUnsignedShort();
        this.cropHeight = idx.readUnsignedShort();

        this.palette = new int[idx.readUnsignedByte()];

        for (int i = 0; i < this.palette.length - 1; i++) {
            this.palette[i + 1] = idx.readMedium();
        }

        for (int l = 0; l < fileIndex; l++) {
            idx.position += 2;
            data.position += idx.readUnsignedShort() * idx.readUnsignedShort();
            idx.position++;
        }

        this.offsetX = idx.readUnsignedByte();
        this.offsetY = idx.readUnsignedByte();
        this.width = idx.readUnsignedShort();
        this.height = idx.readUnsignedShort();
        int type = idx.readUnsignedByte();

        this.pixels = new byte[this.width * this.height];

        if (type == 0) {
            for (int i = 0; i < this.pixels.length; i++) {
                this.pixels[i] = data.readByte();
            }
        } else if (type == 1) {
            for (int x = 0; x < this.width; x++) {
                for (int y = 0; y < this.height; y++) {
                    this.pixels[x + (y * this.width)] = data.readByte();
                }
            }
        }

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = palette[pixels[y * width + x]];
                int a = pixels[y * width + x] == 0 ? 0 : 1;
                int r = (color & 0xFF0000) >> 16;
                int g = (color & 0xFF00) >> 8;
                int b = (color & 0xFF);

                pixmap.setColor(r / 255f, g / 255f, b / 255f, a);
                pixmap.drawPixel(x, y);
            }
        }
        spriteHelper = new SpriteHelper(pixmap);
        spriteHelper.getSprite().setFlip(false, true);
        pixmap.dispose();
    }

    public void crop() {
        if (this.width == this.cropWidth && this.height == this.cropHeight) {
            return;
        }

        byte[] pixels = new byte[this.cropWidth * this.cropHeight];

        int i = 0;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                pixels[x + this.offsetX + (y + this.offsetY) * this.cropWidth] = this.pixels[i++];
            }
        }

        this.pixels = pixels;
        this.width = this.cropWidth;
        this.height = this.cropHeight;
        this.offsetX = 0;
        this.offsetY = 0;
    }

    public void draw(int x, int y) {
        draw(Rt3.batch, x, y);
    }

    public void draw(SpriteBatch batch, int x, int y) {
        x += this.offsetX;
        y += this.offsetY;
        if (width <= 0 || height <= 0) {
            return;
        }

        spriteHelper.draw(batch, x, y, 1f, spriteHelper.getSprite().isFlipX(), spriteHelper.getSprite().isFlipY());
    }

    public Bitmap flipHorizontally() {
        spriteHelper.getSprite().setFlip(!spriteHelper.getSprite().isFlipX(), spriteHelper.getSprite().isFlipY());
        return this;
    }

    public Bitmap flipVertically() {
        spriteHelper.getSprite().setFlip(spriteHelper.getSprite().isFlipX(), !spriteHelper.getSprite().isFlipY());
        return this;
    }

    public void shrink() {
        this.cropWidth >>= 1;
        this.cropHeight >>= 1;

        byte pixels[] = new byte[this.cropWidth * this.cropHeight];
        int i = 0;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                pixels[(x + this.offsetX >> 1) + (y + this.offsetY >> 1) * this.cropWidth] = this.pixels[i++];
            }
        }

        this.pixels = pixels;
        this.width = this.cropWidth;
        this.height = this.cropHeight;
        this.offsetX = 0;
        this.offsetY = 0;
    }

    public void translateRgb(int red, int green, int blue) {
        for (int i = 0; i < this.palette.length; i++) {
            int r = (this.palette[i] >> 16 & 0xff) + red;
            int g = (this.palette[i] >> 8 & 0xff) + green;
            int b = (this.palette[i] & 0xff) + blue;
            r = r > 255 ? 255 : r < 0 ? 0 : r;
            g = g > 255 ? 255 : g < 0 ? 0 : g;
            b = b > 255 ? 255 : b < 0 ? 0 : b;
            this.palette[i] = (r << 16) + (g << 8) + b;
        }
    }
}
