package info.demmonic.hdrs;

import info.demmonic.hdrs.input.Keyboard;
import info.demmonic.hdrs.input.Mouse;
import info.demmonic.hdrs.util.FrameCounter;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;

public abstract class GameShell {

    public static final int WIDTH = 765;
    public static final int HEIGHT = 503;
    private static final long serialVersionUID = 8263129451912051795L;
    public static FrameCounter counter;
    public Color color;
    public boolean focused = true;
    public int idleCycle;
    public Keyboard keyboard;
    public Mouse mouse;
    public boolean redraw = true;

    public GameShell init() {
        this.color = new Color(140, 17, 17);
        return this;
    }

    public abstract void startup();

    public abstract void process();

    public abstract void draw();

    public abstract void redraw();

    public abstract void shutdown();

    public void startThread(Runnable runnable, int priority) {
        Thread t = new Thread(runnable);
        t.start();
        t.setPriority(priority);
        t.setName(runnable.getClass().getSimpleName());
    }

    public abstract Socket getSocket(int port) throws IOException;

}
