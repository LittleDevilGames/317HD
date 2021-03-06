package info.demmonic.hdrs.input.model;

import info.demmonic.hdrs.input.Keyboard;
import info.demmonic.hdrs.util.BitUtils;

import java.lang.reflect.Field;

import static java.awt.event.KeyEvent.*;

public class Key {

    public static final byte SAFE_KEYS = (byte) 0x1;
    public static final byte NUMBER_KEYS = (byte) 0x2;
    public static final byte ALL_KEYS = (byte) 0x3;

    public static final Key LEFT = new Key(VK_LEFT, 1);
    public static final Key RIGHT = new Key(VK_RIGHT, 2);
    public static final Key UP = new Key(VK_UP, 3);
    public static final Key DOWN = new Key(VK_DOWN, 4);
    public static final Key CTRL = new Key(VK_CONTROL, 5);
    public static final Key BACKSPACE = new Key(VK_BACK_SPACE, 8);
    public static final Key DELETE = new Key(VK_DELETE, 8);
    public static final Key TAB = new Key(VK_TAB, 9);
    public static final Key ENTER = new Key(VK_ENTER, 10);
    public int action;
    public int index;

    public Key(int index, int action) {
        this.index = index;
        this.action = action;
    }

    public static void append(StringBuilder string, int key, int flags) {
        if (BitUtils.flagged(flags, SAFE_KEYS) && BitUtils.flagged(flags, NUMBER_KEYS)) {
            if (Key.isNumeral(key) || Key.isSafe(key)) {
                string.append((char) key);
                return;
            }
        }

        if (BitUtils.flagged(flags, NUMBER_KEYS)) {
            if (Key.isNumeral(key)) {
                string.append((char) key);
                return;
            }
        }

        if (BitUtils.flagged(flags, SAFE_KEYS)) {
            if (Key.isSafe(key)) {
                string.append((char) key);
                return;
            }
        }
    }

    public static final void init() {
        try {
            for (Field f : Key.class.getFields()) {
                if (f.getType() == Key.class) {
                    Keyboard.add((Key) f.get(Key.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final boolean isNumeral(int key) {
        return (key >= 48 && key <= 57);
    }

    public static final boolean isSafe(int key) {
        return (key >= 32 && key <= 122);
    }

    public boolean isDown() {
        return Keyboard.isDown(this.action);
    }

    @Override
    public String toString() {
        return "[Key: " + index + ", action: " + action + "]";
    }

}
