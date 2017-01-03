package info.demmonic.hdrs.util;

import lombok.experimental.UtilityClass;

/**
 * @author Demmonic
 */
@UtilityClass
public class ColorUtils {

    public static int adjustHslLightness(int hsl, int l) {
        l = 127 - l;
        l = (l * (hsl & 0x7f)) / 0xA0;

        if (l < 2) {
            l = 2;
        } else if (l > 126) {
            l = 126;
        }

        return (hsl & 0xff80) + l;
    }

    public static int setHslLightnessWithTransparency(int hsl, int l) {
        if (hsl == -1) {
            return 12345678;
        }

        l = l * (hsl & 0x7f) / 128;

        if (l < 2) {
            l = 2;
        } else if (l > 126) {
            l = 126;
        }

        return (hsl & 0xff80) + l;
    }

    public static int trimHsl(int hue, int saturation, int lightness) {
        if (lightness > 179) {
            saturation /= 2;
        }
        if (lightness > 192) {
            saturation /= 2;
        }
        if (lightness > 217) {
            saturation /= 2;
        }
        if (lightness > 243) {
            saturation /= 2;
        }
        return (hue / 4 << 10) + (saturation / 32 << 7) + lightness / 2;
    }

    public static int getRgb(int hsl, int brightness) {
        if (hsl == -2) {
            return 12345678;
        }

        if (hsl == -1) {
            if (brightness < 0) {
                brightness = 0;
            } else if (brightness > 127) {
                brightness = 127;
            }
            brightness = 127 - brightness;
            return brightness;
        }

        brightness = brightness * (hsl & 0x7f) / 128;

        if (brightness < 2) {
            brightness = 2;
        } else if (brightness > 126) {
            brightness = 126;
        }

        return (hsl & 0xff80) + brightness;
    }

    public static int linearRgbBrightness(int rgb, double brightness) {
        double r = Math.pow((double) (rgb >> 16) / 256D, brightness);
        double g = Math.pow((double) (rgb >> 8 & 0xff) / 256D, brightness);
        double b = Math.pow((double) (rgb & 0xff) / 256D, brightness);
        return ((int) (r * 256D) << 16) + ((int) (g * 256D) << 8) + (int) (b * 256D);
    }

}
