package info.demmonic.hdrs.media;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import info.demmonic.hdrs.Rt3;
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
    private SpriteHelper spriteHelper;
    private boolean blackRemoved;
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
        createHelper();
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
            createHelper();
            return;
        } catch (Exception e) {
            System.out.println("Error converting jpg");
        }
    }

    public Sprite(int[] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        createHelper();
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

    public void createHelper() {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = pixels[y * width + x];
                int r = (color & 0xFF0000) >> 16;
                int g = (color & 0xFF00) >> 8;
                int b = (color & 0xFF);
                if (color != -1) {
                    pixmap.setColor(r / 255f, g / 255f, b / 255f, 1f);
                    pixmap.drawPixel(x, y);
                }
            }
        }
        spriteHelper = new SpriteHelper(pixmap);
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

        int height = this.height;
        int width = this.width;
        int dstOff = x + y * Canvas2D.width;
        int srcOff = 0;
        int dstStep = Canvas2D.width - width;
        int srcStep = 0;
        int color = 0;

        if (width <= 0 || height <= 0) {
            return;
        }

        draw(pixels, bitmap.pixels, srcOff, dstOff, width, height, dstStep, srcStep);
    }


    //todo clean all this shit up lol
    public void draw(SpriteBatch batch, int x, int y) {
        draw(batch, x, y, 255, false, true);
    }

    public void draw(int x, int y, int alpha) {
        draw(Rt3.batch, x, y, alpha, false, true);
    }

    public void draw(SpriteBatch batch, int x, int y, boolean flipX) {
        draw(batch, x, y, 255, flipX, true);
    }

    public void draw(SpriteBatch batch, int x, int y, int alpha, boolean flipX, boolean flipY) {
        x += this.offsetX;
        y += this.offsetY;

        if (width <= 0 || height <= 0) {
            return;
        }

        spriteHelper.draw(batch, x, y, alpha / 255f, flipX, flipY);
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
        } catch (Exception e) {
            return;
        }
    }

    public void draw(int x, int y, int width, int height, int radians, int zoom, int pivotX, int pivotY, int ai[], int ai1[]) {

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

        if (width <= 0 || height <= 0) {
            return;
        }
        if (!blackRemoved) {
            Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
            for (int y2 = 0; y2 < height; y2++) {
                for (int x2 = 0; x2 < width; x2++) {
                    int pixel = spriteHelper.getPixmap().getPixel(x2, y2);
                    if (pixel == 255) {

                    } else {
                        pixmap.drawPixel(x2, y2, pixel);
                    }
                }
            }
            spriteHelper.dispose();
            spriteHelper = new SpriteHelper(pixmap);
            blackRemoved = true;
        }
        spriteHelper.draw(Rt3.batch, x, y);
        //draw(this.pixels, srcOff, dstOff, width, height, dstStep, srcStep, DrawType.IGNORE_BLACK);
    }

    public void prepare() {
        prepare(this.width, this.height, this.pixels);
        int redOffset = (int) (Math.random() * 11D) - 5;
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
