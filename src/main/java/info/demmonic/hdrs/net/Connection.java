package info.demmonic.hdrs.net;

import info.demmonic.hdrs.GameShell;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection implements Runnable {

    public GameShell applet;
    public byte buffer[];
    public boolean closed = false;
    public int cycle;
    public boolean error = false;
    public InputStream in;
    public OutputStream out;
    public int position;
    public Socket socket;
    public boolean writing = false;

    public Connection(GameShell applet, Socket socket) throws IOException {
        this.applet = applet;
        this.socket = socket;
        this.socket.setSoTimeout(30000);
        this.socket.setTcpNoDelay(true);
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
    }

    public void close() {
        closed = true;
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException _ex) {
            System.out.println("Error closing stream");
        }
        writing = false;
        synchronized (this) {
            notify();
        }
        buffer = null;
    }

    public int getAvailable() throws IOException {
        if (closed) {
            return 0;
        }
        return in.available();
    }

    public int getByte() throws IOException {
        if (closed) {
            return 0;
        }
        return in.read();
    }

    public int getBytes(byte dest[], int off, int len) throws IOException {
        if (closed) {
            return -1;
        }

        int read = 0;
        for (int i; len > 0; len -= i) {
            i = in.read(dest, off, len);

            if (i <= 0) {
                throw new IOException("EOF");
            }

            off += i;
            read += i;
        }

        return read;
    }

    public void putBytes(byte[] src, int off, int len) throws IOException {
        if (closed) {
            return;
        }
        if (error) {
            error = false;
            throw new IOException("Error in writer thread");
        }
        if (buffer == null) {
            buffer = new byte[5000];
        }
        synchronized (this) {
            for (int i = 0; i < len; i++) {
                buffer[position] = src[i + off];
                position = (position + 1) % 5000;
                if (position == (cycle + 4900) % 5000) {
                    throw new IOException("buffer overflow");
                }
            }

            if (!writing) {
                writing = true;
                applet.startThread(this, 3);
            }
            notify();
        }
    }

    public void run() {
        while (writing) {
            int len, off;

            synchronized (this) {
                if (position == cycle) {
                    try {
                        wait();
                    } catch (InterruptedException ignored) {
                    }
                }

                if (!writing) {
                    return;
                }

                off = cycle;

                if (position >= cycle) {
                    len = position - cycle;
                } else {
                    len = 5000 - cycle;
                }
            }

            if (len > 0) {
                try {
                    out.write(buffer, off, len);
                } catch (IOException _ex) {
                    error = true;
                }

                cycle = (cycle + len) % 5000;

                try {
                    if (position == cycle) {
                        out.flush();
                    }
                } catch (IOException _ex) {
                    error = true;
                }
            }
        }
    }
}
