package rs.media;

import rs.cache.Archive;
import rs.io.Buffer;

import java.awt.*;
import java.awt.image.PixelGrabber;

public class Sprite extends Canvas2D {

    public int cropHeight;
    public int cropWidth;
    public int width;
    public int height;
    public int offsetX;
    public int offsetY;
    public int[] pixels;

    public Sprite(Archive archive, String image_archive, int image_index) {
        Buffer data = new Buffer(archive.get(image_archive + ".dat", null));
        Buffer idx = new Buffer(archive.get("index.dat", null));
        idx.position = data.readUnsignedShort();

        this.cropWidth = idx.readUnsignedShort();
        this.cropHeight = idx.readUnsignedShort();

        int palette[] = new int[idx.readUnsignedByte()];

        for (int k = 0; k < palette.length - 1; k++) {
            palette[k + 1] = idx.readMedium();

            if (palette[k + 1] == 0) {
                palette[k + 1] = 1;
            }
        }

        for (int i = 0; i < image_index; i++) {
            idx.position += 2;
            data.position += idx.readUnsignedShort() * idx.readUnsignedShort();
            idx.position++;
        }

        this.offsetX = idx.readUnsignedByte();
        this.offsetY = idx.readUnsignedByte();
        this.width = idx.readUnsignedShort();
        this.height = idx.readUnsignedShort();
        int type = idx.readUnsignedByte();

        this.pixels = new int[this.width * this.height];

        if (type == 0) {
            for (int i = 0; i < this.pixels.length; i++) {
                this.pixels[i] = palette[data.readUnsignedByte()];
            }
        } else if (type == 1) {
            for (int x = 0; x < this.width; x++) {
                for (int y = 0; y < this.height; y++) {
                    this.pixels[x + (y * this.width)] = palette[data.readUnsignedByte()];
                }
            }
        }
    }

    public Sprite(byte[] data, Component c) {
        try {
            Image i = Toolkit.getDefaultToolkit().createImage(data);
            MediaTracker t = new MediaTracker(c);
            t.addImage(i, 0);
            t.waitForAll();
            this.width = i.getWidth(c);
            this.height = i.getHeight(c);
            this.cropWidth = width;
            this.cropHeight = height;
            this.offsetX = 0;
            this.offsetY = 0;
            this.pixels = new int[width * height];
            PixelGrabber g = new PixelGrabber(i, 0, 0, width, height, pixels, 0, width);
            g.grabPixels();
            return;
        } catch (Exception _ex) {
            System.out.println("Error converting jpg");
        }
    }

    public Sprite(int w, int h) {
        this.pixels = new int[w * h];
        this.cropWidth = w;
        this.cropHeight = h;
        this.width = this.cropWidth;
        this.height = this.cropHeight;
        this.offsetX = 0;
        this.offsetY = 0;
    }

    public void crop() {
        int pixels[] = new int[this.cropWidth * this.cropHeight];

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                pixels[(y + this.offsetY) * this.cropWidth + (x + this.offsetX)] = this.pixels[x + (y * this.width)];
            }
        }

        this.pixels = pixels;
        this.width = this.cropWidth;
        this.height = this.cropHeight;
        this.offsetX = 0;
        this.offsetY = 0;
    }

    public void drawTo(Bitmap bitmap, int x, int y) {
        x += this.offsetX;
        y += this.offsetY;

        int dst_off = x + y * Canvas2D.width;
        int src_off = 0;

        int height = this.height;
        int width = this.width;

        int dstStep = Canvas2D.width - width;
        int srcStep = 0;

        if (y < Canvas2D.leftY) {
            int i = Canvas2D.leftY - y;
            height -= i;
            y = Canvas2D.leftY;
            src_off += i * width;
            dst_off += i * Canvas2D.width;
        }

        if (y + height > Canvas2D.rightY) {
            height -= (y + height) - Canvas2D.rightY;
        }

        if (x < Canvas2D.leftX) {
            int i = Canvas2D.leftX - x;
            width -= i;
            x = Canvas2D.leftX;
            src_off += i;
            dst_off += i;
            srcStep += i;
            dstStep += i;
        }

        if (x + width > Canvas2D.rightX) {
            int i = (x + width) - Canvas2D.rightX;
            width -= i;
            srcStep += i;
            dstStep += i;
        }

        if (width <= 0 || height <= 0) {
            return;
        }

        Canvas2D.draw(pixels, bitmap.pixels, src_off, dst_off, width, height, dstStep, srcStep);
    }

    public void draw(int x, int y) {
        x += this.offsetX;
        y += this.offsetY;

        int height = this.height;
        int width = this.width;

        int dstOff = x + y * Canvas2D.width;
        int dstStep = Canvas2D.width - width;

        int srcOff = 0;
        int src_step = 0;

        if (y < Canvas2D.leftY) {
            int yOffset = Canvas2D.leftY - y;
            height -= yOffset;
            y = Canvas2D.leftY;
            srcOff += yOffset * width;
            dstOff += yOffset * Canvas2D.width;
        }

        if (y + height > Canvas2D.rightY) {
            height -= (y + height) - Canvas2D.rightY;
        }

        if (x < Canvas2D.leftX) {
            int xOffset = Canvas2D.leftX - x;
            width -= xOffset;
            x = Canvas2D.leftX;
            srcOff += xOffset;
            dstOff += xOffset;
            src_step += xOffset;
            dstStep += xOffset;
        }

        if (x + width > Canvas2D.rightX) {
            int widthOffset = (x + width) - Canvas2D.rightX;
            width -= widthOffset;
            src_step += widthOffset;
            dstStep += widthOffset;
        }

        if (width <= 0 || height <= 0) {
            return;
        }

        Canvas2D.draw(pixels, srcOff, dstOff, width, height, dstStep, src_step, DrawType.RGB);
    }

    public void draw(int x, int y, int alpha) {
        x += this.offsetX;
        y += this.offsetY;

        int dstOff = x + y * Canvas2D.width;
        int srcOff = 0;

        int height = this.height;
        int width = this.width;

        int dstStep = Canvas2D.width - width;
        int srcStep = 0;

        if (y < Canvas2D.leftY) {
            int i = Canvas2D.leftY - y;
            height -= i;
            y = Canvas2D.leftY;
            srcOff += i * width;
            dstOff += i * Canvas2D.width;
        }

        if (y + height > Canvas2D.rightY) {
            height -= (y + height) - Canvas2D.rightY;
        }

        if (x < Canvas2D.leftX) {
            int i = Canvas2D.leftX - x;
            width -= i;
            x = Canvas2D.leftX;
            srcOff += i;
            dstOff += i;
            srcStep += i;
            dstStep += i;
        }

        if (x + width > Canvas2D.rightX) {
            int i = (x + width) - Canvas2D.rightX;
            width -= i;
            srcStep += i;
            dstStep += i;
        }

        if (width <= 0 || height <= 0) {
            return;
        }

        Canvas2D.alpha = alpha;
        Canvas2D.draw(this.pixels, srcOff, dstOff, width, height, dstStep, srcStep, DrawType.TRANSLUCENT_IGNORE_BLACK);
    }

    public void drawRotated(int x, int y, int pivotX, int pivotY, int width, int height, int zoom, double angle) {
        try {
            int centerY = -width / 2;
            int centerX = -height / 2;

            int sin = (int) (Math.sin(angle) * 65536D);
            int cos = (int) (Math.cos(angle) * 65536D);

            sin = sin * zoom >> 8;
            cos = cos * zoom >> 8;

            int srcOffX = (pivotX << 16) + (centerX * sin + centerY * cos);
            int srcOffY = (pivotY << 16) + (centerX * cos - centerY * sin);

            int dst_off = x + y * Canvas2D.width;

            for (y = 0; y < height; y++) {
                int i = dst_off;
                int offX = srcOffX;
                int offY = srcOffY;

                for (x = -width; x < 0; x++) {
                    int color = this.pixels[(offX >> 16) + (offY >> 16) * this.width];

                    if (color != 0) {
                        Canvas2D.pixels[i++] = color;
                    } else {
                        i++;
                    }

                    offX += cos;
                    offY -= sin;
                }

                srcOffX += sin;
                srcOffY += cos;
                dst_off += Canvas2D.width;
            }

            return;
        } catch (Exception _ex) {
            return;
        }
    }

    public void draw(int x, int y, int width, int height, int radians, int zoom, int pivot_x, int pivot_y, int ai[], int ai1[]) {
        try {
            int centerX = -width / 2;
            int centerY = -height / 2;

            int sin = (int) (Math.sin((double) radians / 326.11000000000001D) * 65536D);
            int cos = (int) (Math.cos((double) radians / 326.11000000000001D) * 65536D);
            sin = sin * zoom >> 8;
            cos = cos * zoom >> 8;

            int srcOffX = (pivot_x << 16) + (centerY * sin + centerX * cos);
            int srcOffY = (pivot_y << 16) + (centerY * cos - centerX * sin);

            int dstOff = x + y * Canvas2D.width;

            for (y = 0; y < height; y++) {
                int i4 = ai1[y];
                int dst_offset = dstOff + i4;
                int offsetX = srcOffX + cos * i4;
                int offsetY = srcOffY - sin * i4;

                for (x = -ai[y]; x < 0; x++) {
                    Canvas2D.pixels[dst_offset++] = this.pixels[(offsetX >> 16) + (offsetY >> 16) * this.width];
                    offsetX += cos;
                    offsetY -= sin;
                }

                srcOffX += sin;
                srcOffY += cos;
                dstOff += Canvas2D.width;
            }
        } catch (Exception _ex) {
        }
    }

    public void drawMasked(int x, int y) {
        x += this.offsetX;
        y += this.offsetY;
        int dstOff = x + y * Canvas2D.width;
        int srcOff = 0;
        int height = this.height;
        int width = this.width;
        int dstStep = Canvas2D.width - width;
        int srcStep = 0;

        if (y < Canvas2D.leftY) {
            int yOffset = Canvas2D.leftY - y;
            height -= yOffset;
            y = Canvas2D.leftY;
            srcOff += yOffset * width;
            dstOff += yOffset * Canvas2D.width;
        }

        if (y + height > Canvas2D.rightY) {
            height -= (y + height) - Canvas2D.rightY;
        }

        if (x < Canvas2D.leftX) {
            int xOffset = Canvas2D.leftX - x;
            width -= xOffset;
            x = Canvas2D.leftX;
            srcOff += xOffset;
            dstOff += xOffset;
            srcStep += xOffset;
            dstStep += xOffset;
        }

        if (x + width > Canvas2D.rightX) {
            int xOffset = (x + width) - Canvas2D.rightX;
            width -= xOffset;
            srcStep += xOffset;
            dstStep += xOffset;
        }

        if (width <= 0 || height <= 0) {
            return;
        }

        Canvas2D.draw(this.pixels, srcOff, dstOff, width, height, dstStep, srcStep, DrawType.IGNORE_BLACK);
    }

    public void prepare() {
        Canvas2D.prepare(this.width, this.height, this.pixels);
    }

    public void translateRgb(int r, int g, int b) {
        for (int i = 0; i < pixels.length; i++) {
            int color = pixels[i];
            if (color != 0) {
                int red = color >> 16 & 0xff;
                red += r;
                if (red < 1) {
                    red = 1;
                } else if (red > 255) {
                    red = 255;
                }
                int green = color >> 8 & 0xff;
                green += g;
                if (green < 1) {
                    green = 1;
                } else if (green > 255) {
                    green = 255;
                }
                int blue = color & 0xff;
                blue += b;
                if (blue < 1) {
                    blue = 1;
                } else if (blue > 255) {
                    blue = 255;
                }
                pixels[i] = (red << 16) + (green << 8) + blue;
            }
        }
    }
}
