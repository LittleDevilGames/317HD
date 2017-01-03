package info.demmonic.hdrs.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BitUtils {

    public static boolean flagged(int num, int bit) {
        return (num & bit) == bit;
    }

}
