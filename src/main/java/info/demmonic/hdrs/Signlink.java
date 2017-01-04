package info.demmonic.hdrs;

import info.demmonic.hdrs.cache.Cache;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Signlink implements Runnable {

    public static boolean active;
    public static Cache.AccessFile cacheFile = null;
    public static Cache.AccessFile cacheIndex[] = new Cache.AccessFile[5];
    public static String resolvedDns = null;
    public static String dnsNext = null;
    public static boolean error = true;
    public static String errorName = "";
    public static Socket socket = null;
    public static InetAddress socketAddress;
    public static int socketPort;
    public static Runnable thread = null;
    public static int threadIndex;
    public static int threadPriority = 1;
    public static int uid;

    public static void error(String s) {
        if (!error) {
            return;
        }
        if (!active) {
            return;
        }
        System.out.println("Error: " + s);
    }

    public static File getCacheFolder() {
        return new File(getCachePath());
    }

    public static String getCachePath() {
        return ("./cache/");
    }

    public static synchronized void getDns(String s) {
        resolvedDns = s;
        dnsNext = s;
    }

    public static int getUniqueId(String s) {
        try {
            File file = new File(s + "uid.dat");
            if (!file.exists() || file.length() < 4L) {
                DataOutputStream out = new DataOutputStream(new FileOutputStream(s + "uid.dat"));
                out.writeInt((int) (Math.random() * 99999999D));
                out.close();
            }
        } catch (Exception e) {
        }
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(s + "uid.dat"));
            int i = in.readInt();
            in.close();
            return i + 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public static void start(InetAddress inetaddress) {
        threadIndex = (int) (Math.random() * 99999999D);

        if (active) {
            try {
                Thread.sleep(500L);
            } catch (Exception ignored) {
            }
            active = false;
        }

        socketPort = 0;
        thread = null;
        dnsNext = null;
        socketAddress = inetaddress;

        Thread t = new Thread(new Signlink());
        t.setDaemon(true);
        t.start();

        while (!active) {
            try {
                Thread.sleep(50L);
            } catch (Exception ignored) {
            }
        }
    }

    public void run() {
        active = true;
        String path = getCachePath();
        uid = getUniqueId(path);
        new File(path).mkdirs();

        try {
            cacheFile = new Cache.AccessFile(path + "main_file_cache.dat");

            for (int j = 0; j < 5; j++) {
                cacheIndex[j] = new Cache.AccessFile(path + "main_file_cache.idx" + j);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = threadIndex; threadIndex == i; ) {
            if (socketPort != 0) {
                try {
                    socket = new Socket(socketAddress, socketPort);
                } catch (Exception e) {
                    socket = null;
                }
                socketPort = 0;
            } else if (thread != null) {
                Thread t = new Thread(thread);
                t.setDaemon(true);
                t.start();
                t.setPriority(threadPriority);
                thread = null;
            } else if (dnsNext != null) {
                try {
                    resolvedDns = InetAddress.getByName(dnsNext).getHostName();
                } catch (Exception e) {
                    resolvedDns = "unknown";
                }
                dnsNext = null;
            }
            try {
                Thread.sleep(50L);
            } catch (Exception ignored) {
            }
        }

    }

}
