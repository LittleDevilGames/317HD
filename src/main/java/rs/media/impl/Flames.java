package rs.media.impl;

import rs.Game;
import rs.media.Bitmap;
import rs.media.Canvas2D;
import rs.media.ImageProducer;
import rs.media.Sprite;
import rs.util.RSColor;

import java.util.Arrays;

public class Flames {

    public static int cycle;
    public static int[] dissolveMask, lastDissolveMask;
    public static int[] pixels;
    public static ImageProducer[] producer;
    public static Bitmap[] bitmapRune;
    public static Sprite[] image;
    public static int[] palette, paletteRed, paletteGreen, paletteBlue;
    public static int[] intensityMap;
    public static int[] distortionMap = new int[256];
    public static int cycleGreen, cycleBlue, runeCycle;

    public static void nullify() {
        Game.processFlames = false;

        while (Game.flameThread) {
            Game.processFlames = false;

            try {
                Thread.sleep(50);
            } catch (Exception e) {
                /* ignore */
            }
        }

        TitleScreen.bitmapBox = null;
        TitleScreen.bitmapButton = null;

        bitmapRune = null;
        palette = null;
        paletteRed = null;
        paletteGreen = null;
        paletteBlue = null;
        dissolveMask = null;
        lastDissolveMask = null;
        pixels = null;
        intensityMap = null;
        image = null;
    }

    public static void createProducers() {
        producer = new ImageProducer[2];
        producer[0] = new ImageProducer(128, 265);
        Canvas2D.clear();
        producer[1] = new ImageProducer(128, 265);
        Canvas2D.clear();
    }

    public static void createImages() {
        bitmapRune = new Bitmap[12];

        image = new Sprite[2];

        for (int i = 0; i < image.length; i++) {
            image[i] = new Sprite(128, 265);
            System.arraycopy(producer[i].pixels, 0, image[i].pixels, 0, 128 * 265);
        }

        int i = 0;

        try {
            i = Integer.parseInt(Game.instance.getParameter("fl_icon"));
        } catch (Exception e) {
            /* ignore */
        }

        if (i == 0) {
            for (int j = 0; j < bitmapRune.length; j++) {
                bitmapRune[j] = new Bitmap(Game.archive, "runes", j);
            }
        } else {
            for (int j = 0; j < bitmapRune.length; j++) {
                bitmapRune[j] = new Bitmap(Game.archive, "runes", 12 + (j & 3));
            }
        }

        createPalettes();
    }

    public static void createPalettes() {
        paletteRed = new int[256];

        for (int i = 0; i < 64; i++) {
            paletteRed[i] = i * 0x040000;
        }
        for (int i = 0; i < 64; i++) {
            paletteRed[i + 64] = 0xff0000 + (0x0400 * i);
        }
        for (int i = 0; i < 64; i++) {
            paletteRed[i + 128] = 0xffff00 + (0x4 * i);
        }
        for (int i = 0; i < 64; i++) {
            paletteRed[i + 192] = 0xffffff;
        }

        paletteGreen = new int[256];
        for (int i = 0; i < 64; i++) {
            paletteGreen[i] = i * 1024;
        }
        for (int i = 0; i < 64; i++) {
            paletteGreen[i + 64] = 65280 + 4 * i;
        }
        for (int i = 0; i < 64; i++) {
            paletteGreen[i + 128] = 65535 + 0x40000 * i;
        }
        for (int i = 0; i < 64; i++) {
            paletteGreen[i + 192] = 0xffffff;
        }

        paletteBlue = new int[256];
        for (int i = 0; i < 64; i++) {
            paletteBlue[i] = i * 4;
        }
        for (int i = 0; i < 64; i++) {
            paletteBlue[i + 64] = 255 + 0x40000 * i;
        }
        for (int i = 0; i < 64; i++) {
            paletteBlue[i + 128] = 0xff00ff + 1024 * i;
        }
        for (int i = 0; i < 64; i++) {
            paletteBlue[i + 192] = 0xffffff;
        }

        palette = new int[256];
        pixels = new int[128 * 256];
        dissolveMask = new int[pixels.length];
        lastDissolveMask = new int[pixels.length];
        drawRune(null);
        intensityMap = new int[pixels.length];
    }

    public static void drawRune(Bitmap bitmap) {
        Arrays.fill(dissolveMask, 0);

        for (int i = 0; i < 5000; i++) {
            dissolveMask[(int) ((Math.random() * 128D) * 256D)] = (int) (Math.random() * 256D);
        }

        for (int i = 0; i < 20; i++) {
            for (int y = 1; y < 256 - 1; y++) {
                for (int x = 1; x < 127; x++) {
                    int j = x + (y << 7);
                    lastDissolveMask[j] = (dissolveMask[j - 1] + dissolveMask[j + 1] + dissolveMask[j - 128] + dissolveMask[j + 128]) >> 2;
                }
            }

            int[] mask = dissolveMask;
            dissolveMask = lastDissolveMask;
            lastDissolveMask = mask;
        }

        if (bitmap != null) {
            int i = 0;
            for (int y = 0; y < bitmap.height; y++) {
                for (int x = 0; x < bitmap.width; x++) {
                    if (bitmap.pixels[i++] != 0) {
                        dissolveMask[(x + 16 + bitmap.offsetX) + ((y + 16 + bitmap.offsetY) << 7)] = 0;
                    }
                }
            }
        }
    }

    public static void handle() {
        int height = 256;

        // Pixels starts off as an intensity map.

        // Generate the base of the flame
        for (int x = 10; x < 117; x++) {
            int k = (int) (Math.random() * 100D);
            if (k < 50) {
                pixels[x + (height - 2 << 7)] = 255;
            }
        }

        // Add little flame sparklets in random places.
        for (int l = 0; l < 100; l++) {
            int x = (int) (Math.random() * 124D) + 2;
            int y = (int) (Math.random() * 128D) + 128;
            pixels[x + (y << 7)] = 192;
        }

        // Blur the intensity map.
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < 127; x++) {
                int i = x + (y << 7);
                intensityMap[i] = (pixels[i - 1] + pixels[i + 1] + pixels[i - 128] + pixels[i + 128]) / 4;
            }

        }

        runeCycle += 128;
        if (runeCycle > dissolveMask.length) {
            runeCycle -= dissolveMask.length;
            drawRune(bitmapRune[(int) (Math.random() * 12D)]);
        }

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < 127; x++) {
                int i = x + (y << 7);
                int j = intensityMap[i + 128] - dissolveMask[i + runeCycle & dissolveMask.length - 1] / 5;

                if (j < 0) {
                    j = 0;
                }

                pixels[i] = j;
            }

        }

        for (int y = 0; y < height - 1; y++) {
            distortionMap[y] = distortionMap[y + 1];
        }

        distortionMap[height - 1] = (int) (Math.sin((double) Game.loopCycle / 14D) * 16D + Math.sin((double) Game.loopCycle / 15D) * 14D + Math.sin((double) Game.loopCycle / 16D) * 12D);

        if (cycleGreen > 0) {
            cycleGreen -= 4;
        }

        if (cycleBlue > 0) {
            cycleBlue -= 4;
        }

        if (cycleGreen == 0 && cycleBlue == 0) {
            int l3 = (int) (Math.random() * 2000D);
            if (l3 == 0) {
                cycleGreen = 1024;
            }
            if (l3 == 1) {
                cycleBlue = 1024;
            }
        }
    }

    public static void handlePalette() {
        if (cycleGreen > 0) {
            for (int i = 0; i < 256; i++) {
                if (cycleGreen > 768) {
                    palette[i] = RSColor.mix(paletteRed[i], paletteGreen[i], 1024 - cycleGreen);
                } else if (cycleGreen > 256) {
                    palette[i] = paletteGreen[i];
                } else {
                    palette[i] = RSColor.mix(paletteGreen[i], paletteRed[i], 256 - cycleGreen);
                }
            }
        } else if (cycleBlue > 0) {
            for (int i = 0; i < 256; i++) {
                if (cycleBlue > 768) {
                    palette[i] = RSColor.mix(paletteRed[i], paletteBlue[i], 1024 - cycleBlue);
                } else if (cycleBlue > 256) {
                    palette[i] = paletteBlue[i];
                } else {
                    palette[i] = RSColor.mix(paletteBlue[i], paletteRed[i], 256 - cycleBlue);
                }
            }
        } else {
            System.arraycopy(paletteRed, 0, palette, 0, palette.length);
        }

        System.arraycopy(image[0].pixels, 0, producer[0].pixels, 0, producer[0].pixels.length);

        int height = 256;
        int src_off = 0;
        int dst_off = 1152;
        for (int y = 1; y < height - 1; y++) {
            int l1 = (distortionMap[y] * (height - y)) / height;
            int i = 24 + l1;

            if (i < 0) {
                i = 0;
            }

            src_off += i;

            for (int j = i; j < 128; j++) {
                int rgb = pixels[src_off++];

                if (rgb != 0) {
                    int old = rgb;
                    int alpha = 256 - rgb;
                    rgb = palette[rgb];
                    int src = producer[0].pixels[dst_off];
                    producer[0].pixels[dst_off++] = ((rgb & 0xff00ff) * old + (src & 0xff00ff) * alpha & 0xff00ff00) + ((rgb & 0xff00) * old + (src & 0xff00) * alpha & 0xff0000) >> 8;
                } else {
                    dst_off++;
                }
            }

            dst_off += i;
        }

        producer[0].draw(0, 0);

        System.arraycopy(image[1].pixels, 0, producer[1].pixels, 0, producer[1].pixels.length);

        src_off = 0;
        dst_off = 1176;
        for (int y = 1; y < height - 1; y++) {
            int i3 = (distortionMap[y] * (height - y)) / height;
            int i = 103 - i3;
            dst_off += i3;
            for (int j = 0; j < i; j++) {
                int rgb = pixels[src_off++];
                if (rgb != 0) {
                    int old = rgb;
                    int alpha = 256 - rgb;
                    rgb = palette[rgb];
                    int src = producer[1].pixels[dst_off];
                    producer[1].pixels[dst_off++] = ((rgb & 0xff00ff) * old + (src & 0xff00ff) * alpha & 0xff00ff00) + ((rgb & 0xff00) * old + (src & 0xff00) * alpha & 0xff0000) >> 8;
                } else {
                    dst_off++;
                }
            }

            src_off += 128 - i;
            dst_off += 128 - i - i3;
        }

        producer[1].draw(637, 0);
    }
}
