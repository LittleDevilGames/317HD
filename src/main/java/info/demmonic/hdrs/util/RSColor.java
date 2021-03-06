package info.demmonic.hdrs.util;

import info.demmonic.hdrs.Game;

public class RSColor {

    public static final int BLACK = 0;
    public static final int SCROLLBAR_BACKGROUND = 0x23201B;

    public static final int SCROLLBAR_FOREGROUND = 0x4D4233;
    public static final int SCROLLBAR_HIGHLIGHT = 0x766654;
    public static final int SCROLLBAR_LOWLIGHT = 0x332D25;
    public static final int WHITE = 0xFFFFFF;

    public static String getLevelTag(int level) {
        int difference = Game.self.combatLevel - level;
        StringBuilder s = new StringBuilder(" ");
        if (difference < -9) {
            s.append("@red@");
        } else if (difference < -6) {
            s.append("@or3@");
        } else if (difference < -3) {
            s.append("@or2@");
        } else if (difference < 0) {
            s.append("@or1@");
        } else if (difference > 9) {
            s.append("@gre@");
        } else if (difference > 6) {
            s.append("@gr3@");
        } else if (difference > 3) {
            s.append("@gr2@");
        } else if (difference > 0) {
            s.append("@gr1@");
        } else {
            s.append("@yel@");
        }
        return s.append("(level-").append(level).append(")").toString();
    }

    public static int mix(int a, int b, int opacity) {
        int alpha = 256 - opacity;
        return ((a & 0xff00ff) * alpha + (b & 0xff00ff) * opacity & 0xff00ff00) + ((a & 0xff00) * alpha + (b & 0xff00) * opacity & 0xff0000) >> 8;
    }

    public static int getTagColor(String s) {
        switch (s) {
            case "red":
                return 0xff0000;
            case "gre":
                return 65280;
            case "blu":
                return 255;
            case "yel":
                return 0xffff00;
            case "cya":
                return 65535;
            case "mag":
                return 0xff00ff;
            case "whi":
                return 0xffffff;
            case "bla":
                return 0;
            case "lre":
                return 0xff9040;
            case "dre":
                return 0x800000;
            case "dbl":
                return 128;
            case "or1":
                return 0xffb000;
            case "or2":
                return 0xff7000;
            case "or3":
                return 0xff3000;
            case "gr1":
                return 0xc0ff00;
            case "gr2":
                return 0x80ff00;
            case "gr3":
                return 0x40ff00;
            default:
                return -1;
        }
    }

}
