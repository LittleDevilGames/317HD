package info.demmonic.hdrs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import info.demmonic.hdrs.input.Keyboard;
import info.demmonic.hdrs.input.Mouse;

/**
 * @author Demmonic
 */
public class Rt3 extends com.badlogic.gdx.Game {

    public static SpriteBatch batch;
    private GameShell shell = new Game();
    private OrthographicCamera camera;

    @Override
    public void create() {
        Signlink.start(null);
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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

}
