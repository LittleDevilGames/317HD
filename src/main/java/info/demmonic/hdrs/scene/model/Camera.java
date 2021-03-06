package info.demmonic.hdrs.scene.model;

import info.demmonic.hdrs.util.MathUtils;

public class Camera {

    public static final int MAX_PITCH = 383;
    public static final int MIN_PITCH = 128;

    public static int pitchModifier, yawModifier;
    public static int x, y, z, pitch, yaw;

    public static void set(int x, int y, int z, int zoom, int pitch, int yaw) {
        int a = 2048 - pitch & 0x7ff;
        int b = 2048 - yaw & 0x7ff;
        int c = 0;
        int d = 0;

        if (a != 0) {
            int e = MathUtils.sin[a];
            int f = MathUtils.cos[a];
            int g = d * f - zoom * e >> 16;
            zoom = d * e + zoom * f >> 16;
            d = g;
        }

        if (b != 0) {
            int e = MathUtils.sin[b];
            int f = MathUtils.cos[b];
            int g = zoom * e + c * f >> 16;
            zoom = zoom * f - c * e >> 16;
            c = g;
        }

        Camera.x = x - c;
        Camera.z = z - d;
        Camera.y = y - zoom;
        Camera.pitch = pitch;
        Camera.yaw = yaw;
    }

}
