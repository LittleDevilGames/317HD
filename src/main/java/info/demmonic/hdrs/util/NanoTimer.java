package info.demmonic.hdrs.util;

public class NanoTimer {

    private long lastTime;

    public NanoTimer() {
        this.start();
    }

    public static void sleep(long time) {
        if (time > 0L) {
            if (time % 10L != 0L) {
                threadSleep(time);
            } else {
                threadSleep(time - 1L);
                threadSleep(1L);
            }
        }
    }

    public static void threadSleep(long l) {
        try {
            Thread.sleep(l);
        } catch (Exception e) {
        }
    }

    public final void reset() {
        this.start();
    }

    public final int sleep(int sleepTimer, int delay) {
        long sleep = (long) sleepTimer * 1000000L;
        long diff = this.lastTime - System.nanoTime();

        int count = 0;

        if (diff < sleep) {
            diff = sleep;
        }

        sleep(diff / 1_000_000L);

        long currentTime;
        for (currentTime = System.nanoTime(); count < 10 && (count < 1 || currentTime > this.lastTime); this.lastTime += (long) delay * 1_000_000L) {
            count++;
        }

        if (this.lastTime < currentTime) {
            this.lastTime = currentTime;
        }
        return count;
    }

    public final void start() {
        this.lastTime = System.nanoTime();
    }
}
