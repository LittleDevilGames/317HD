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

    /**
     * Creates a new Bitmap. (A Bitmap is a space saving image which uses a palette of 256 colors and a color depth of 24.)
     *
     * @param archive       the archive to load from.
     * @param image_archive the image archive within the archive.
     * @param file_index    the image index within the image archive.
     */
    public Bitmap(Archive archive, String image_archive, int file_index) {
        Buffer data = new Buffer(archive.get(image_archive + ".dat", null));
        Buffer idx = new Buffer(archive.get("index.dat", null));

        // The index of this bitmap's header is stored in the first 2 bytes of the data file.
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

    /**
     * Crops the image to the specified width and height.
     */
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

    /**
     * Draws the image.
     *
     * @param x the x position.
     * @param y the y position.
     */
    public void draw(int x, int y) {
        x += this.offsetX;
        y += this.offsetY;

        int dst_off = x + y * Canvas2D.width;
        int src_off = 0;

        int height = this.height;
        int width = this.width;

        int dst_step = Canvas2D.width - width;
        int src_step = 0;

        if (y < Canvas2D.left_y) {
            int y_diff = Canvas2D.left_y - y;
            height -= y_diff;
            y = Canvas2D.left_y;
            src_off += y_diff * width;
            dst_off += y_diff * Canvas2D.width;
        }

        if (y + height > Canvas2D.right_y) {
            height -= (y + height) - Canvas2D.right_y;
        }

        if (x < Canvas2D.left_x) {
            int x_diff = Canvas2D.left_x - x;
            width -= x_diff;
            x = Canvas2D.left_x;
            src_off += x_diff;
            dst_off += x_diff;
            src_step += x_diff;
            dst_step += x_diff;
        }

        if (x + width > Canvas2D.right_x) {
            int x_diff = (x + width) - Canvas2D.right_x;
            width -= x_diff;
            src_step += x_diff;
            dst_step += x_diff;
        }

        if (width <= 0 || height <= 0) {
            return;
        }

        draw(this.pixels, this.palette, src_off, dst_off, width, height, dst_step, src_step);
    }

    /**
     * Flips the image horizontally.
     */
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

    /**
     * Flips the image vertically.
     */
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

    /**
     * Shrinks the image to half its original size.
     */
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

    /**
     * Adjusts the palette's color.
     *
     * @param red   the red modifier.
     * @param green the green modifier.
     * @param blue  the blue modifier.
     */
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
