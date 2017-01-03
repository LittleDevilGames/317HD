package rs.media;

import rs.cache.Archive;
import rs.io.Buffer;

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

    public Bitmap(Archive archive, String image_archive) {
        this(archive, image_archive, 0);
    }

    public Bitmap(Archive archive, String image_archive, int file_index) {
        Buffer data = new Buffer(archive.get(image_archive + ".dat", null));
        Buffer idx = new Buffer(archive.get("index.dat", null));

        idx.position = data.readUnsignedShort();

        this.cropWidth = idx.readUnsignedShort();
        this.cropHeight = idx.readUnsignedShort();

        this.palette = new int[idx.readUnsignedByte()];

        for (int i = 0; i < this.palette.length - 1; i++) {
            this.palette[i + 1] = idx.readMedium();
        }

        for (int l = 0; l < file_index; l++) {
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
        x += this.offsetX;
        y += this.offsetY;

        int dst_off = x + y * Canvas2D.width;
        int src_off = 0;

        int height = this.height;
        int width = this.width;

        int dst_step = Canvas2D.width - width;
        int src_step = 0;

        if (y < Canvas2D.leftY) {
            int y_diff = Canvas2D.leftY - y;
            height -= y_diff;
            y = Canvas2D.leftY;
            src_off += y_diff * width;
            dst_off += y_diff * Canvas2D.width;
        }

        if (y + height > Canvas2D.rightY) {
            height -= (y + height) - Canvas2D.rightY;
        }

        if (x < Canvas2D.leftX) {
            int x_diff = Canvas2D.leftX - x;
            width -= x_diff;
            x = Canvas2D.leftX;
            src_off += x_diff;
            dst_off += x_diff;
            src_step += x_diff;
            dst_step += x_diff;
        }

        if (x + width > Canvas2D.rightX) {
            int x_diff = (x + width) - Canvas2D.rightX;
            width -= x_diff;
            src_step += x_diff;
            dst_step += x_diff;
        }

        if (width <= 0 || height <= 0) {
            return;
        }

        draw(this.pixels, this.palette, src_off, dst_off, width, height, dst_step, src_step);
    }

    public Bitmap flipHorizontally() {
        byte pixels[] = new byte[this.width * this.height];

        int i = 0;
        for (int y = 0; y < this.height; y++) {
            for (int x = this.width - 1; x >= 0; x--) {
                pixels[i++] = this.pixels[x + (y * this.width)];
            }
        }

        this.pixels = pixels;
        this.offsetX = this.cropWidth - this.width - this.offsetX;
        return this;
    }

    public Bitmap flipVertically() {
        byte pixels[] = new byte[this.width * this.height];

        int i = 0;
        for (int y = this.height - 1; y >= 0; y--) {
            for (int x = 0; x < this.width; x++) {
                pixels[i++] = this.pixels[x + (y * this.width)];
            }
        }

        this.pixels = pixels;
        this.offsetY = this.cropHeight - this.height - this.offsetY;
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
