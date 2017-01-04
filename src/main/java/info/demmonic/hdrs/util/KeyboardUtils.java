package info.demmonic.hdrs.util;

import com.badlogic.gdx.Input;
import lombok.experimental.UtilityClass;

import java.awt.event.KeyEvent;


/**
 * Created by chris on 1/4/17.
 */
@UtilityClass
public class KeyboardUtils {

    public static int translateKeyCode(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_ADD:
                return KeyEvent.VK_ADD;
            case KeyEvent.VK_SUBTRACT:
                return Input.Keys.MINUS;
            case Input.Keys.NUM_0:
                return KeyEvent.VK_0;
            case Input.Keys.NUM_1:
                return KeyEvent.VK_1;
            case Input.Keys.NUM_2:
                return KeyEvent.VK_2;
            case Input.Keys.NUM_3:
                return KeyEvent.VK_3;
            case Input.Keys.NUM_4:
                return KeyEvent.VK_4;
            case Input.Keys.NUM_5:
                return KeyEvent.VK_5;
            case Input.Keys.NUM_6:
                return KeyEvent.VK_6;
            case Input.Keys.NUM_7:
                return KeyEvent.VK_7;
            case Input.Keys.NUM_8:
                return KeyEvent.VK_8;
            case Input.Keys.NUM_9:
                return KeyEvent.VK_9;
            case Input.Keys.A:
                return KeyEvent.VK_A;
            case Input.Keys.B:
                return KeyEvent.VK_B;
            case Input.Keys.C:
                return KeyEvent.VK_C;
            case Input.Keys.D:
                return KeyEvent.VK_D;
            case Input.Keys.E:
                return KeyEvent.VK_E;
            case Input.Keys.F:
                return KeyEvent.VK_F;
            case Input.Keys.G:
                return KeyEvent.VK_G;
            case Input.Keys.H:
                return KeyEvent.VK_H;
            case Input.Keys.I:
                return KeyEvent.VK_I;
            case Input.Keys.J:
                return KeyEvent.VK_J;
            case Input.Keys.K:
                return KeyEvent.VK_K;
            case Input.Keys.L:
                return KeyEvent.VK_L;
            case Input.Keys.M:
                return KeyEvent.VK_M;
            case Input.Keys.N:
                return KeyEvent.VK_N;
            case Input.Keys.O:
                return KeyEvent.VK_O;
            case Input.Keys.P:
                return KeyEvent.VK_P;
            case Input.Keys.Q:
                return KeyEvent.VK_Q;
            case Input.Keys.R:
                return KeyEvent.VK_R;
            case Input.Keys.S:
                return KeyEvent.VK_S;
            case Input.Keys.T:
                return KeyEvent.VK_T;
            case Input.Keys.U:
                return KeyEvent.VK_U;
            case Input.Keys.V:
                return KeyEvent.VK_V;
            case Input.Keys.W:
                return KeyEvent.VK_W;
            case Input.Keys.X:
                return KeyEvent.VK_X;
            case Input.Keys.Y:
                return KeyEvent.VK_Y;
            case Input.Keys.Z:
                return KeyEvent.VK_Z;
            case Input.Keys.ALT_LEFT:
                return KeyEvent.VK_ALT;
            case KeyEvent.VK_ALT_GRAPH:
                return Input.Keys.ALT_RIGHT;
            case Input.Keys.BACKSLASH:
                return KeyEvent.VK_BACK_SLASH;
            case Input.Keys.COMMA:
                return KeyEvent.VK_COMMA;
            case Input.Keys.FORWARD_DEL:
                return KeyEvent.VK_DELETE;
            case Input.Keys.DPAD_LEFT:
                return KeyEvent.VK_LEFT;
            case Input.Keys.DPAD_RIGHT:
                return KeyEvent.VK_RIGHT;
            case Input.Keys.DPAD_UP:
                return KeyEvent.VK_UP;
            case Input.Keys.DPAD_DOWN:
                return KeyEvent.VK_DOWN;
            case Input.Keys.ENTER:
                return KeyEvent.VK_ENTER;
            case Input.Keys.HOME:
                return KeyEvent.VK_HOME;
            case Input.Keys.MINUS:
                return KeyEvent.VK_MINUS;
            case Input.Keys.PERIOD:
                return KeyEvent.VK_PERIOD;
            case Input.Keys.PLUS:
                return KeyEvent.VK_PLUS;
            case Input.Keys.SEMICOLON:
                return KeyEvent.VK_SEMICOLON;
            case Input.Keys.SHIFT_LEFT:
                return KeyEvent.VK_SHIFT;
            case Input.Keys.SHIFT_RIGHT:
                return KeyEvent.VK_SHIFT;
            case Input.Keys.SLASH:
                return KeyEvent.VK_SLASH;
            case Input.Keys.SPACE:
                return KeyEvent.VK_SPACE;
            case Input.Keys.TAB:
                return KeyEvent.VK_TAB;
            case Input.Keys.DEL:
                return KeyEvent.VK_DELETE;
            case Input.Keys.CONTROL_LEFT:
                return KeyEvent.VK_CONTROL;
            case Input.Keys.ESCAPE:
                return KeyEvent.VK_ESCAPE;
            case Input.Keys.END:
                return KeyEvent.VK_END;
            case Input.Keys.INSERT:
                return KeyEvent.VK_INSERT;
            case Input.Keys.PAGE_UP:
                return KeyEvent.VK_PAGE_UP;
            case Input.Keys.PAGE_DOWN:
                return KeyEvent.VK_PAGE_DOWN;
            case Input.Keys.F1:
                return KeyEvent.VK_F1;
            case Input.Keys.F2:
                return KeyEvent.VK_F2;
            case Input.Keys.F3:
                return KeyEvent.VK_F3;
            case Input.Keys.F4:
                return KeyEvent.VK_F4;
            case Input.Keys.F5:
                return KeyEvent.VK_F5;
            case Input.Keys.F6:
                return KeyEvent.VK_F6;
            case Input.Keys.F7:
                return KeyEvent.VK_F7;
            case Input.Keys.F8:
                return KeyEvent.VK_F8;
            case Input.Keys.F9:
                return KeyEvent.VK_F9;
            case Input.Keys.F10:
                return KeyEvent.VK_F10;
            case Input.Keys.F11:
                return KeyEvent.VK_F11;
            case Input.Keys.F12:
                return KeyEvent.VK_F12;
            case Input.Keys.COLON:
                return KeyEvent.VK_COLON;
            case Input.Keys.NUMPAD_0:
                return KeyEvent.VK_0;
            case Input.Keys.NUMPAD_1:
                return KeyEvent.VK_1;
            case Input.Keys.NUMPAD_2:
                return KeyEvent.VK_2;
            case Input.Keys.NUMPAD_3:
                return KeyEvent.VK_3;
            case Input.Keys.NUMPAD_4:
                return KeyEvent.VK_4;
            case Input.Keys.NUMPAD_5:
                return KeyEvent.VK_5;
            case Input.Keys.NUMPAD_6:
                return KeyEvent.VK_6;
            case Input.Keys.NUMPAD_7:
                return KeyEvent.VK_7;
            case Input.Keys.NUMPAD_8:
                return KeyEvent.VK_8;
            case Input.Keys.NUMPAD_9:
                return KeyEvent.VK_9;
        }
        return Input.Keys.UNKNOWN;
    }
}