package info.demmonic.hdrs.net;

import info.demmonic.hdrs.GameShell;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Jaggrab {

    public static Socket socket;
    protected static GameShell shell;

    public static DataInputStream request(String s) throws IOException {
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception _ex) {
            }
            socket = null;
        }
        socket = Jaggrab.shell.getSocket(43595);
        socket.setSoTimeout(10000);
        socket.getOutputStream().write(("JAGGRAB /" + s + "\n\n").getBytes());

        return new DataInputStream(socket.getInputStream());
    }

    public static void setShell(GameShell shell) {
        Jaggrab.shell = shell;
    }

}
