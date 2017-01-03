package info.demmonic.hdrs.input;

public class MouseRecorder implements Runnable {

    public boolean active = true;
    public int cycle;
    public int lastX, lastY;
    public int off;
    public final Object synchronize = new Object();
    public int[] x = new int[500];
    public int[] y = new int[500];

    public void run() {
        while (active) {
            synchronized (synchronize) {
                if (off < 500) {
                    x[off] = Mouse.lastX;
                    y[off] = Mouse.lastY;
                    off++;
                }
            }
            try {
                Thread.sleep(50L);
            } catch (Exception _ex) {
            }
        }
    }
}
