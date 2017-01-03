package info.demmonic.hdrs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import info.demmonic.hdrs.input.Keyboard;
import info.demmonic.hdrs.input.Mouse;

/**
 * @author Demmonic
 */
public class Rt3 extends com.badlogic.gdx.Game {

    private GameShell shell = new Game();

    @Override
    public void create() {
        Signlink.start(null);
        shell.startup();
        System.out.println("started");
    }

    @Override
    public void render() {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Mouse.process();
        shell.process();
        Keyboard.process();

        shell.draw();
    }

}
