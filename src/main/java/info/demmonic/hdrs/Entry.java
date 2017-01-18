package info.demmonic.hdrs;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import lombok.experimental.UtilityClass;

/**
 * @author Demmonic
 */
@UtilityClass
public class Entry {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Athenis";
        config.width = 765;
        config.height = 503;
        new LwjglApplication(new Rt3(), config);
    }

}
