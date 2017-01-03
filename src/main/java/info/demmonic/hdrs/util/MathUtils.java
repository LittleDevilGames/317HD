package info.demmonic.hdrs.util;

import lombok.experimental.UtilityClass;

/**
 * @author Demmonic
 */
@UtilityClass
public class MathUtils {

    public static int[] sin;
    public static int[] cos;

    static {
        sin = new int[2048];
        cos = new int[2048];

        for (int i = 0; i < 2048; i++) {
            sin[i] = (int) (65536D * Math.sin((double) i * 0.0030679615D));
            cos[i] = (int) (65536D * Math.cos((double) i * 0.0030679615D));
        }

    }

    public static int getLerpedCosine(int a, int b, int x, int frequency) {
        int f = (65536 - cos[x * 1024 / frequency] >> 1);
        return (a * (65536 - f) >> 16) + (b * f >> 16);
    }

    public static int getNoise(int a, int b, int amplitude) {
        int x = a / amplitude;
        int x1 = a & amplitude - 1;
        int y = b / amplitude;
        int x2 = b & amplitude - 1;
        int a1 = getNoise2d(x, y);
        int b1 = getNoise2d(x + 1, y);
        int a2 = getNoise2d(x, y + 1);
        int b2 = getNoise2d(x + 1, y + 1);
        int a3 = getLerpedCosine(a1, b1, x1, amplitude);
        int b3 = getLerpedCosine(a2, b2, x1, amplitude);
        return getLerpedCosine(a3, b3, x2, amplitude);
    }

    public static int getNoise2d(int x, int y) {
        int a = (getPerlinNoise(x - 1, y - 1) + getPerlinNoise(x + 1, y - 1) + getPerlinNoise(x - 1, y + 1) + getPerlinNoise(x + 1, y + 1));
        int b = (getPerlinNoise(x - 1, y) + getPerlinNoise(x + 1, y) + getPerlinNoise(x, y - 1) + getPerlinNoise(x, y + 1));
        int c = getPerlinNoise(x, y);
        return a / 16 + b / 8 + c / 4;
    }

    public static int getNoiseHeight(int x, int y) {
        int height = (getNoise(x + 45365, y + 91923, 4) - 128 + (getNoise(x + 10294, y + 37821, 2) - 128 >> 1) + (getNoise(x, y, 1) - 128 >> 2));
        height = (int) ((double) height * 0.3) + 35;

        if (height < 10) {
            height = 10;
        } else if (height > 60) {
            height = 60;
        }

        return height;
    }

    public static int getPerlinNoise(int x, int y) {
        int a = x + y * 57;
        a = a << 13 ^ a;
        int b = a * (a * a * 15731 + 789221) + 1376312589 & 0x7fffffff;
        return b >> 19 & 0xFF;
    }

}
