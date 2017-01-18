package info.demmonic.hdrs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import info.demmonic.hdrs.input.Keyboard;
import info.demmonic.hdrs.input.Mouse;
import info.demmonic.hdrs.input.model.Key;
import info.demmonic.hdrs.media.Canvas2D;
import info.demmonic.hdrs.util.KeyboardUtils;

import java.awt.event.KeyEvent;

/**
 * @author Demmonic
 */
public class Rt3 extends com.badlogic.gdx.Game implements InputProcessor {

    public static SpriteBatch batch;
    public static OrthographicCamera camera;
    private FPSLogger logger;
    private GameShell shell = new Game();


    @Override
    public void create() {
        Signlink.start(null);
        batch = new SpriteBatch();
        Canvas2D.createTexture();
        logger = new FPSLogger();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(this);
        shell.startup();
    }

    @Override
    public void render() {
        GL20 gl = Gdx.gl;
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Mouse.process();
        shell.process();
        Keyboard.process();

        shell.draw();
        logger.log();
    }


    @Override
    public boolean keyDown(int keycode) {
        keycode = KeyboardUtils.translateKeyCode(keycode);
        int keychar = (char) keycode;

        if (!Keyboard.isShiftDown && keycode == KeyEvent.VK_SHIFT) {
            Keyboard.isShiftDown = true;
        }

        if (org.lwjgl.input.Keyboard.isKeyDown(org.lwjgl.input.Keyboard.KEY_CAPITAL)) {
            keychar = Character.toUpperCase(keychar);
            if (Keyboard.isShiftDown) {
                keychar = Character.toLowerCase(keychar);
            }
        } else {
            keychar = Character.toLowerCase(keychar);
            if (Keyboard.isShiftDown) {
                keychar = Character.toUpperCase(keychar);
            }
        }

        if (Keyboard.isShiftDown) {
            if (keycode == KeyEvent.VK_1) {
                keychar = '!';
            } else if (keycode == KeyEvent.VK_2) {
                keychar = '@';
            } else if (keycode == KeyEvent.VK_3) {
                keychar = '#';
            } else if (keycode == KeyEvent.VK_4) {
                keychar = '$';
            } else if (keycode == KeyEvent.VK_5) {
                keychar = '%';
            } else if (keycode == KeyEvent.VK_6) {
                keychar = '^';
            } else if (keycode == KeyEvent.VK_7) {
                keychar = '&';
            } else if (keycode == KeyEvent.VK_8) {
                keychar = '*';
            } else if (keycode == KeyEvent.VK_9) {
                keychar = '(';
            } else if (keycode == KeyEvent.VK_0) {
                keychar = ')';
            } else if (keycode == KeyEvent.VK_EQUALS) {
                keychar = '+';
            } else if (keycode == KeyEvent.VK_OPEN_BRACKET) {
                keychar = '{';
            } else if (keycode == KeyEvent.VK_CLOSE_BRACKET) {
                keychar = '}';
            } else if (keycode == KeyEvent.VK_BACK_SLASH) {
                keychar = '|';
            } else if (keycode == KeyEvent.VK_SEMICOLON) {
                keychar = ':';
            } else if (keycode == KeyEvent.VK_QUOTEDBL) { //???
                keychar = '"';
            } else if (keycode == KeyEvent.VK_COMMA) {
                keychar = '<';
            } else if (keycode == KeyEvent.VK_PERIOD) {
                keychar = '>';
            } else if (keycode == KeyEvent.VK_SLASH) {
                keychar = '?';
            }
        }

        if (keychar < 30) {
            keychar = 0;
        }

        boolean isAction = false;
        for (Key k : Keyboard.keys) {
            if (k.index == keycode) {
                keychar = k.action;
                isAction = true;
                break;
            }
        }

        if (!isAction) {
            if (keycode >= 112 && keycode <= 123) {
                keychar = ((1008 + keycode) - 112);
            } else if (keycode == KeyEvent.VK_HOME) {
                keychar = 1000;
            } else if (keycode == KeyEvent.VK_END) {
                keychar = 1001;
            } else if (keycode == KeyEvent.VK_PAGE_UP) {
                keychar = 1002;
            } else if (keycode == KeyEvent.VK_PAGE_DOWN) {
                keychar = 1003;
            }
        }

        if (keychar > 0 && keychar < 128) {
            Keyboard.action[keychar] = 1;
        }

        if (keychar > 4) {
            Keyboard.buffer[Keyboard.lastPosition] = keychar;
            Keyboard.lastPosition = Keyboard.lastPosition + 1 & 0x7F;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        keycode = KeyboardUtils.translateKeyCode(keycode);
        if (Keyboard.isShiftDown && keycode == 16) {
            Keyboard.isShiftDown = false;
        }

        char c = (char) keycode;
        if (c < '\036') {
            c = '\0';
        } else if (keycode == 37) {
            c = '\001';
        } else if (keycode == 39) {
            c = '\002';
        } else if (keycode == 38) {
            c = '\003';
        } else if (keycode == 40) {
            c = '\004';
        } else if (keycode == 17) {
            c = '\005';
        } else if (keycode == 8) {
            c = '\b';
        } else if (keycode == 127) {
            c = '\b';
        } else if (keycode == 9) {
            c = '\t';
        } else if (keycode == 10) {
            c = '\n';
        }

        if (c > 0 && c < 128) {
            Keyboard.action[c] = 0;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            Mouse.lastClickX = screenX;
            Mouse.lastClickY = screenY;
            Mouse.lastClickTime = System.currentTimeMillis();
            Mouse.lastClickButton = 1;
            Mouse.dragButton = 1;

            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            Mouse.dragButton = 0;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Mouse.lastX = screenX;
        Mouse.lastY = screenY;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Mouse.lastX = screenX;
        Mouse.lastY = screenY;
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        Mouse.wheelAmount = amount;
        return true;
    }
}
