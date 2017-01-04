package info.demmonic.hdrs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import info.demmonic.hdrs.input.Keyboard;
import info.demmonic.hdrs.input.Mouse;
import info.demmonic.hdrs.input.model.Key;
import info.demmonic.hdrs.util.KeyboardUtils;

import java.awt.event.KeyEvent;

/**
 * @author Demmonic
 */
public class Rt3 extends com.badlogic.gdx.Game implements InputProcessor {

    public static SpriteBatch batch;
    private GameShell shell = new Game();
    private OrthographicCamera camera;

    @Override
    public void create() {
        Signlink.start(null);
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(this);
        shell.startup();
        System.out.println("started");
    }

    @Override
    public void render() {
        GL20 gl = Gdx.gl;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Mouse.process();
        shell.process();
        Keyboard.process();

        shell.draw();
    }


    @Override
    public boolean keyDown(int keycode) {
        keycode = KeyboardUtils.translateKeyCode(keycode);
        int keychar = (char) keycode;

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
        System.out.println(keycode);
        keycode = KeyboardUtils.translateKeyCode(keycode);
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
