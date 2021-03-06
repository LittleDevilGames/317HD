package info.demmonic.hdrs.cache.impl;

import info.demmonic.hdrs.cache.Archive;
import info.demmonic.hdrs.io.Buffer;
import info.demmonic.hdrs.util.ColorUtils;

public class Floor {

    public static int count;
    public static Floor[] instance;
    public int color;
    public int color2;
    public int hue;
    public int hueDivisor;
    public int hue2;
    public int lightness;
    public String name;
    public int saturation;
    public boolean showUnderlay;
    public byte textureIndex;

    public Floor(Buffer buffer) {
        this.defaults();

        do {
            int opcode = buffer.readUnsignedByte();
            if (opcode == 0) {
                return;
            }
            if (opcode == 1) {
                setColor(this.color2 = buffer.readMedium());
            } else if (opcode == 2) {
                this.textureIndex = buffer.readByte();
            } else if (opcode == 3) {
            } else if (opcode == 5) {
                this.showUnderlay = false;
            } else if (opcode == 6) {
                this.name = buffer.readString();
            } else if (opcode == 7) {
                int hue2 = this.hue2;
                int saturation = this.saturation;
                int lightness = this.lightness;
                int hue = this.hue;
                setColor(buffer.readMedium());
                this.hue2 = hue2;
                this.saturation = saturation;
                this.lightness = lightness;
                this.hue = hue;
                this.hueDivisor = hue;
            } else {
                System.out.println("Error unrecognised config code: " + opcode);
            }
        } while (true);
    }

    public static void unpack(Archive a) {
        Buffer s = new Buffer(a.get("flo.dat", null));

        Floor.count = s.readUnsignedShort();
        Floor.instance = new Floor[Floor.count];

        for (int i = 0; i < Floor.count; i++) {
            Floor.instance[i] = new Floor(s);
        }
    }

    public void defaults() {
        textureIndex = -1;
        showUnderlay = true;
    }



    public void setColor(int rgb) {
        double red = (double) (rgb >> 16 & 0xff) / 256D;
        double green = (double) (rgb >> 8 & 0xff) / 256D;
        double blue = (double) (rgb & 0xff) / 256D;
        double d3 = red;

        if (green < d3) {
            d3 = green;
        }

        if (blue < d3) {
            d3 = blue;
        }

        double d4 = red;

        if (green > d4) {
            d4 = green;
        }

        if (blue > d4) {
            d4 = blue;
        }

        double d5 = 0.0D;
        double d6 = 0.0D;
        double d7 = (d3 + d4) / 2D;

        if (d3 != d4) {
            if (d7 < 0.5D) {
                d6 = (d4 - d3) / (d4 + d3);
            }
            if (d7 >= 0.5D) {
                d6 = (d4 - d3) / (2D - d4 - d3);
            }
            if (red == d4) {
                d5 = (green - blue) / (d4 - d3);
            } else if (green == d4) {
                d5 = 2D + (blue - red) / (d4 - d3);
            } else if (blue == d4) {
                d5 = 4D + (red - green) / (d4 - d3);
            }
        }

        d5 /= 6D;
        this.hue2 = (int) (d5 * 256D);
        this.saturation = (int) (d6 * 256D);
        this.lightness = (int) (d7 * 256D);

        if (this.saturation < 0) {
            this.saturation = 0;
        } else if (saturation > 255) {
            this.saturation = 255;
        }

        if (this.lightness < 0) {
            this.lightness = 0;
        } else if (this.lightness > 255) {
            this.lightness = 255;
        }

        if (d7 > 0.5D) {
            this.hueDivisor = (int) ((1.0D - d7) * d6 * 512D);
        } else {
            this.hueDivisor = (int) (d7 * d6 * 512D);
        }

        if (this.hueDivisor < 1) {
            this.hueDivisor = 1;
        }

        this.hue = (int) (d5 * (double) this.hueDivisor);

        int hue = (this.hue2 + (int) (Math.random() * 16D)) - 8;

        if (hue < 0) {
            hue = 0;
        } else if (hue > 255) {
            hue = 255;
        }

        int saturation = (this.saturation + (int) (Math.random() * 48D)) - 24;

        if (saturation < 0) {
            saturation = 0;
        } else if (saturation > 255) {
            saturation = 255;
        }

        int lightness = (this.lightness + (int) (Math.random() * 48D)) - 24;

        if (lightness < 0) {
            lightness = 0;
        } else if (lightness > 255) {
            lightness = 255;
        }

        this.color = ColorUtils.trimHsl(hue, saturation, lightness);
    }
}
