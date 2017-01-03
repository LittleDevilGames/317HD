package info.demmonic.hdrs.util;

import info.demmonic.hdrs.Game;
import info.demmonic.hdrs.io.Buffer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JString {

    public static final Buffer buffer = new Buffer(new byte[100]);
    public static final char[] FORMAT_BUFFER = new char[100];

    public static final char VALID_CHARACTERS[] = {' ', 'e', 't', 'a', 'o', 'i', 'h', 'n', 's', 'r', 'd', 'l', 'u', 'm', 'w', 'c', 'y', 'f', 'g', 'p', 'b', 'v', 'k', 'x', 'j', 'q', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ' ', '!', '?', '.', ',', ':', ';', '(', ')', '-', '&', '*', '\\', '\'', '@', '#', '+', '=', '\243', '$', '%', '"', '[', ']'};
    public static final char VALID_NAME_CHARACTERS[] = {'_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static String toAsteriks(String s) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            b.append("*");
        }
        return b.toString();
    }

    public static String getFiltered(String s) {
        buffer.position = 0;
        put(s, buffer);
        int start = buffer.position;
        buffer.position = 0;
        return getFormatted(start, buffer);
    }

    public static String getFormatted(String s) {
        if (s.length() > 0) {
            char chars[] = s.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '_') {
                    chars[i] = ' ';
                    if (i + 1 < chars.length && chars[i + 1] >= 'a' && chars[i + 1] <= 'z') {
                        chars[i + 1] = (char) ((chars[i + 1] + 65) - 97);
                    }
                }
            }
            if (chars[0] >= 'a' && chars[0] <= 'z') {
                chars[0] = (char) ((chars[0] + 65) - 97);
            }
            return new String(chars);
        } else {
            return s;
        }
    }

    public static String getFormatted(int length, Buffer b) {
        int off = 0;
        int k = -1;
        for (int i = 0; i < length; i++) {
            int i1 = b.readUnsignedByte();
            int j1 = i1 >> 4 & 0xf;

            if (k == -1) {
                if (j1 < 13) {
                    FORMAT_BUFFER[off++] = VALID_CHARACTERS[j1];
                } else {
                    k = j1;
                }
            } else {
                FORMAT_BUFFER[off++] = VALID_CHARACTERS[((k << 4) + j1) - 195];
                k = -1;
            }

            j1 = i1 & 0xF;

            if (k == -1) {
                if (j1 < 13) {
                    FORMAT_BUFFER[off++] = VALID_CHARACTERS[j1];
                } else {
                    k = j1;
                }
            } else {
                FORMAT_BUFFER[off++] = VALID_CHARACTERS[((k << 4) + j1) - 195];
                k = -1;
            }
        }

        boolean capitalize = true;
        for (int i = 0; i < off; i++) {
            char c = FORMAT_BUFFER[i];

            if (capitalize && c >= 'a' && c <= 'z') {
                FORMAT_BUFFER[i] += '\uFFE0';
                capitalize = false;
            }

            if (c == '.' || c == '!' || c == '?') {
                capitalize = true;
            }
        }

        return new String(FORMAT_BUFFER, 0, off);
    }

    public static String getAddress(int i) {
        return (i >> 24 & 0xff) + "." + (i >> 16 & 0xff) + "." + (i >> 8 & 0xff) + "." + (i & 0xff);
    }

    public static String getFormattedString(long l) {
        return getFormatted(toString(l));
    }

    public static long hash(String s) {
        s = s.toUpperCase();
        long l = 0L;
        for (int i = 0; i < s.length(); i++) {
            l = (l * 61L + (long) s.charAt(i)) - 32L;
            l = l + (l >> 56) & 0xffffffffffffffL;
        }
        return l;
    }

    public static long toLong(String s) {
        long l = 0L;
        for (int i = 0; i < s.length() && i < 12; i++) {
            char c = s.charAt(i);
            l *= 37L;
            if (c >= 'A' && c <= 'Z') {
                l += (1 + c) - 65;
            } else if (c >= 'a' && c <= 'z') {
                l += (1 + c) - 97;
            } else if (c >= '0' && c <= '9') {
                l += (27 + c) - 48;
            }
        }

        for (; l % 37L == 0L && l != 0L; l /= 37L) {
        }
        return l;
    }

    public static String toString(long l) {
        if (l <= 0L || l >= 0x5b5b57f8a98a5dd1L) {
            return "invalid_string";
        }
        if (l % 37L == 0L) {
            return "invalid_string";
        }
        int len = 0;
        char characters[] = new char[12];
        while (l != 0L) {
            long l1 = l;
            l /= 37L;
            characters[11 - len++] = VALID_NAME_CHARACTERS[(int) (l1 - l * 37L)];
        }
        return new String(characters, 12 - len, len);
    }

    public static boolean isVowel(String s, int charIndex) {
        if (charIndex < 0 || charIndex > s.length()) {
            return false;
        }
        char c = s.charAt(charIndex);
        return c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
    }

    public static String localUsername() {
        if (Game.self != null && Game.self.name != null) {
            return Game.self.name;
        }
        return getFormatted(Game.username);
    }

    public static void put(String s, Buffer buffer) {
        if (s.length() > 80) {
            s = s.substring(0, 80);
        }

        s = s.toLowerCase();

        int a = -1;
        for (int i = 0; i < s.length(); i++) {
            int b = 0;
            char c = s.charAt(i);
            for (int l = 0; l < VALID_CHARACTERS.length; l++) {
                if (c != VALID_CHARACTERS[l]) {
                    continue;
                }
                b = l;
                break;
            }

            if (b > 12) {
                b += 195;
            }

            if (a == -1) {
                if (b < 13) {
                    a = b;
                } else {
                    buffer.writeByte(b);
                }
            } else if (b < 13) {
                buffer.writeByte((a << 4) + b);
                a = -1;
            } else {
                buffer.writeByte((a << 4) + (b >> 4));
                a = b & 0xF;
            }
        }

        if (a != -1) {
            buffer.writeByte(a << 4);
        }
    }

}
