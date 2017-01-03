package info.demmonic.hdrs.media;

import info.demmonic.hdrs.cache.Archive;
import info.demmonic.hdrs.io.Buffer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;

public class Sprite extends Canvas2D {

    public int cropHeight;
    public int cropWidth;
    public int width;
    public int height;
    public int offsetX;
    public int offsetY;
    public int[] pixels;

    public Sprite(Archive archive, String imageArchive, int imageIndex) {
        Buffer data = new Buffer(archive.get(imageArchive + ".dat", null));
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

        for (int i = 0; i < imageIndex; i++) {
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

    public Sprite(byte[] data) {
        try {
            BufferedImage i = ImageIO.read(new ByteArrayInputStream(data));
            this.width = i.getWidth();
            this.height = i.getHeight();
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

        int dstOff = x + y * Canvas2D.width;
        int srcOff = 0;

        int height = this.height;
        int width = this.width;

        int dstStep = Canvas2D.width - width;
        int srcStep = 0;

        if (y < leftY) {
            int i = leftY - y;
            height -= i;
            y = leftY;
            srcOff += i * width;
            dstOff += i * Canvas2D.width;
        }

        if (y + height > rightY) {
            height -= (y + height) - rightY;
        }

        if (x < leftX) {
            int i = leftX - x;
            width -= i;
            x = leftX;
            srcOff += i;
            dstOff += i;
            srcStep += i;
            dstStep += i;
        }

        if (x + width > rightX) {
            int i = (x + width) - rightX;
            width -= i;
            srcStep += i;
            dstStep += i;
        }

        if (width <= 0 || height <= 0) {
            return;
        }

        draw(pixels, bitmap.pixels, srcOff, dstOff, width, height, dstStep, srcStep);
    }

    public void draw(int x, int y) {
        x += this.offsetX;
        y += this.offsetY;

        int height = this.height;
        int width = this.width;

        int dstOff = x + y * Canvas2D.width;
        int dstStep = Canvas2D.width - width;

        int srcOff = 0;
        int srcStep = 0;

        if (y < leftY) {
            int yOffset = leftY - y;
            height -= yOffset;
            y = leftY;
            srcOff += yOffset * width;
            dstOff += yOffset * Canvas2D.width;
        }

        if (y + height > rightY) {
            height -= (y + height) - rightY;
        }

        if (x < leftX) {
            int xOffset = leftX - x;
            width -= xOffset;
            x = leftX;
            srcOff += xOffset;
            dstOff += xOffset;
            srcStep += xOffset;
            dstStep += xOffset;
        }

        if (x + width > rightX) {
            int widthOffset = (x + width) - rightX;
            width -= widthOffset;
            srcStep += widthOffset;
            dstStep += widthOffset;
        }

        if (width <= 0 || height <= 0) {
            return;
        }

        draw(pixels, srcOff, dstOff, width, height, dstStep, srcStep, DrawType.RGB);
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

        if (y < leftY) {
            int i = leftY - y;
            height -= i;
            y = leftY;
            srcOff += i * width;
            dstOff += i * Canvas2D.width;
        }

        if (y + height > rightY) {
            height -= (y + height) - rightY;
        }

        if (x < leftX) {
            int i = leftX - x;
            width -= i;
            x = leftX;
            srcOff += i;
            dstOff += i;
            srcStep += i;
            dstStep += i;
        }

        if (x + width > rightX) {
            int i = (x + width) - rightX;
            width -= i;
            srcStep += i;
            dstStep += i;
        }

        if (width <= 0 || height <= 0) {
            return;
        }

        Canvas2D.alpha = alpha;
        draw(this.pixels, srcOff, dstOff, width, height, dstStep, srcStep, DrawType.TRANSLUCENT_IGNORE_BLACK);
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

            int dstOff = x + y * Canvas2D.width;

            for (y = 0; y < height; y++) {
                int i = dstOff;
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
                dstOff += Canvas2D.width;
            }

            return;
        } catch (Exception _ex) {
            return;
        }
    }

    public void draw(int x, int y, int width, int height, int radians, int zoom, int pivotX, int pivotY, int ai[], int ai1[]) {
        try {
            int centerX = -width / 2;
            int centerY = -height / 2;

            int sin = (int) (Math.sin((double) radians / 326.11000000000001D) * 65536D);
            int cos = (int) (Math.cos((double) radians / 326.11000000000001D) * 65536D);
            sin = sin * zoom >> 8;
            cos = cos * zoom >> 8;

            int srcOffX = (pivotX << 16) + (centerY * sin + centerX * cos);
            int srcOffY = (pivotY << 16) + (centerY * cos - centerX * sin);

            int dstOff = x + y * Canvas2D.width;

            for (y = 0; y < height; y++) {
                int i4 = ai1[y];
                int dstOffset = dstOff + i4;
                int offsetX = srcOffX + cos * i4;
                int offsetY = srcOffY - sin * i4;

                for (x = -ai[y]; x < 0; x++) {
                    Canvas2D.pixels[dstOffset++] = this.pixels[(offsetX >> 16) + (offsetY >> 16) * this.width];
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

        if (y < leftY) {
            int yOffset = leftY - y;
            height -= yOffset;
            y = leftY;
            srcOff += yOffset * width;
            dstOff += yOffset * Canvas2D.width;
        }

        if (y + height > rightY) {
            height -= (y + height) - rightY;
        }

        if (x < leftX) {
            int xOffset = leftX - x;
            width -= xOffset;
            x = leftX;
            srcOff += xOffset;
            dstOff += xOffset;
            srcStep += xOffset;
            dstStep += xOffset;
        }

        if (x + width > rightX) {
            int xOffset = (x + width) - rightX;
            width -= xOffset;
            srcStep += xOffset;
            dstStep += xOffset;
        }

        if (width <= 0 || height <= 0) {
            return;
        }

        draw(this.pixels, srcOff, dstOff, width, height, dstStep, srcStep, DrawType.IGNORE_BLACK);
    }

    public void prepare() {
        prepare(this.width, this.height, this.pixels);
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
