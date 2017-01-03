package rs.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ChunkUtils {

    public static int rotateLandX(int x, int y, int type) {
        x &= 0x7;
        y &= 0x7;
        type &= 3;

        if (type == 0) {
            return x;
        }

        if (type == 1) {
            return y;
        }

        if (type == 2) {
            return 7 - x;
        } else {
            return 7 - y;
        }
    }

    public static int rotateLandY(int x, int y, int type) {
        x &= 0x7;
        y &= 0x7;
        type &= 3;

        if (type == 0) {
            return y;
        }

        if (type == 1) {
            return 7 - x;
        }

        if (type == 2) {
            return 7 - y;
        } else {
            return x;
        }
    }

    public static int rotateLocX(int x, int y, int size_x, int size_y, int type) {
        x &= 0x7;
        y &= 0x7;
        type &= 3;

        if (type == 0) {
            return x;
        }

        if (type == 1) {
            return y;
        }

        if (type == 2) {
            return 7 - x - (size_x - 1);
        } else {
            return 7 - y - (size_y - 1);
        }
    }

    public static int rotateLocY(int x, int y, int size_x, int size_y, int type) {
        x &= 0x7;
        y &= 0x7;
        type &= 3;

        if (type == 0) {
            return y;
        }

        if (type == 1) {
            return 7 - x - (size_x - 1);
        }

        if (type == 2) {
            return 7 - y - (size_y - 1);
        } else {
            return x;
        }
    }

}
