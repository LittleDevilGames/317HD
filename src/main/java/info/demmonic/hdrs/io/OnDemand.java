package info.demmonic.hdrs.io;

import info.demmonic.hdrs.Game;
import info.demmonic.hdrs.Signlink;
import info.demmonic.hdrs.cache.Archive;
import info.demmonic.hdrs.node.Chain;
import info.demmonic.hdrs.node.Deque;
import info.demmonic.hdrs.node.impl.OnDemandRequest;
import info.demmonic.hdrs.util.JString;

import java.io.*;
import java.net.Socket;
import java.util.zip.CRC32;
import java.util.zip.GZIPInputStream;

public class OnDemand implements Runnable {

    public static final String[] CRC_FILES = {"model_crc", "anim_crc", "midi_crc", "map_crc"};
    public static final String[] VERSION_FILES = {"model_version", "anim_version", "midi_version", "map_version"};

    public int animIndices[];
    public Buffer buffer;
    public Chain completed;
    public CRC32 crc32;
    public int[][] crcs;
    public OnDemandRequest current;
    public int cycle;
    public int extrasLoaded;
    public int extrasCount;
    public int fails;
    public byte filePriorities[][];
    public int fileVersions[][];
    public Game game;
    public int highestPriority;
    public int idleCycles;
    public Deque immediate;
    public int immediateRequestsSent;
    public InputStream in;
    public long lastSocketOpen;
    public int mapFiles[];
    public int mapIndices[];
    public int landscapeFiles[];
    public byte membersArea[];
    public String message;
    public int midiIndices[];
    public byte modelIndices[];
    public int offset;
    public OutputStream out;
    public Chain passiveRequests;
    public int passiveRequestsSent;
    public boolean retrieving;
    public boolean running;
    public Chain sentRequests;
    public int deadTime;
    public Socket socket;
    public int toRead;
    public Chain toRequest;
    public Chain wanted;

    public OnDemand() {
        sentRequests = new Chain();
        message = JString.BLANK;
        crc32 = new CRC32();
        buffer = new Buffer(500);
        filePriorities = new byte[4][];
        passiveRequests = new Chain();
        running = true;
        retrieving = false;
        completed = new Chain();
        immediate = new Deque();
        fileVersions = new int[4][];
        crcs = new int[4][];
        toRequest = new Chain();
        wanted = new Chain();
    }

    public void clearPassiveRequests() {
        synchronized (passiveRequests) {
            passiveRequests.clear();
        }
    }

    public boolean data(int cacheVersion, int cacheCrc, byte[] data) {
        if (data == null || data.length < 2) {
            return false;
        }

        int pos = data.length - 2;
        int readVersion = ((data[pos] & 0xff) << 8) + (data[pos + 1] & 0xff);

        crc32.reset();
        crc32.update(data, 0, pos);

        int readCrc = (int) crc32.getValue();

        if (readVersion != cacheVersion) {
            return false;
        }

        if (readCrc != cacheCrc) {
            return false;
        }

        return true;
    }

    public int getFileCount(int archive) {
        return fileVersions[archive].length;
    }

    public int getMapUid(int x, int y, int type) {
        int uid = (x << 8) + y;
        for (int i = 0; i < mapIndices.length; i++) {
            if (mapIndices[i] == uid) {
                if (type == 0) {
                    return mapFiles[i];
                } else {
                    return landscapeFiles[i];
                }
            }
        }
        return -1;
    }

    public void handleResponse() {
        try {
            int available = in.available();

            if (toRead == 0 && available >= 6) {
                retrieving = true;

                in.read(buffer.payload, 0, 6);

                buffer.position = 0;
                int archive = buffer.readUnsignedByte();
                int file = buffer.readUnsignedShort();
                int size = buffer.readUnsignedShort();
                int part = buffer.readUnsignedByte();

                current = null;

                for (OnDemandRequest r = (OnDemandRequest) sentRequests.top(); r != null; r = (OnDemandRequest) sentRequests.next()) {
                    if (r.archive == archive && r.file == file) {
                        current = r;
                    }

                    if (current != null) {
                        r.cycle = 0;
                    }
                }

                if (current != null) {
                    idleCycles = 0;
                    if (size == 0) {
                        Signlink.error("Rej: " + archive + "," + file);
                        current.payload = null;
                        if (current.immediate) {
                            synchronized (completed) {
                                completed.pushBack(current);
                            }
                        } else {
                            current.detach();
                        }
                        current = null;
                    } else {
                        if (current.payload == null && part == 0) {
                            current.payload = new byte[size];
                        }
                        if (current.payload == null && part != 0) {
                            throw new IOException("missing start of file");
                        }
                    }
                }
                offset = part * 500;
                toRead = 500;

                int limit = size - part * 500;

                if (toRead > limit) {
                    toRead = limit;
                }
            }

            if (toRead > 0 && available >= toRead) {
                retrieving = true;
                byte[] data = buffer.payload;
                int position = 0;

                if (current != null) {
                    data = current.payload;
                    position = offset;
                }

                for (int i = 0; i < toRead; i += in.read(data, i + position, toRead - i))
                    ;

                if (toRead + offset >= data.length && current != null) {
                    if (Game.cache[0] != null) {
                        Game.cache[current.archive + 1].put(data, current.file);
                    }

                    if (!current.immediate && current.archive == 3) {
                        current.immediate = true;
                        current.archive = 93;
                    }

                    if (current.immediate) {
                        synchronized (completed) {
                            completed.pushBack(current);
                        }
                    } else {
                        current.detach();
                    }
                }
                toRead = 0;
                return;
            }
        } catch (IOException ioe) {
            try {
                socket.close();
            } catch (Exception e) {
            }
            socket = null;
            in = null;
            out = null;
            toRead = 0;
            ioe.printStackTrace();
        }
    }

    public boolean hasLandscape(int index) {
        for (int k = 0; k < mapIndices.length; k++) {
            if (landscapeFiles[k] == index) {
                return true;
            }
        }
        return false;
    }

    public boolean hasMidi(int i) {
        return midiIndices[i] == 1;
    }

    public int immediateRequestCount() {
        synchronized (immediate) {
            return immediate.count();
        }
    }

    public void localComplete() {
        OnDemandRequest r;

        synchronized (wanted) {
            r = (OnDemandRequest) wanted.pop();
        }

        while (r != null) {
            retrieving = true;
            byte data[] = null;

            if (Game.cache[0] != null) {
                data = Game.cache[r.archive + 1].get(r.file);
            }

            if (!data(fileVersions[r.archive][r.file], crcs[r.archive][r.file], data)) {
                data = null;
            }

            synchronized (wanted) {
                if (data == null) {
                    toRequest.pushBack(r);
                } else {
                    r.payload = data;
                    synchronized (completed) {
                        completed.pushBack(r);
                    }
                }
                r = (OnDemandRequest) wanted.pop();
            }
        }
    }

    public int modelFlags(int i) {
        return modelIndices[i] & 0xff;
    }

    public OnDemandRequest next() {
        OnDemandRequest r;

        synchronized (completed) {
            r = (OnDemandRequest) completed.pop();
        }

        if (r == null) {
            return null;
        }

        synchronized (immediate) {
            r.uncache();
        }

        if (r.payload == null) {
            return r;
        }

        GZIPInputStream in = null;
        ByteArrayOutputStream out = null;

        try {
            out = new ByteArrayOutputStream();
            in = new GZIPInputStream(new ByteArrayInputStream(r.payload));

            byte[] buffer = new byte[2048]; // 2kb
            int read;

            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
            throw new RuntimeException("error unzipping", e);
        } finally {
            if (out != null) {
                r.payload = out.toByteArray();

                try {
                    out.close();
                } catch (IOException e) {
                    /* nothing */
                }
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    /* nothing */
                }
            }
        }
        return r;
    }

    public void loadExtras() {
        while (immediateRequestsSent == 0 && passiveRequestsSent < 10) {
            if (highestPriority == 0) {
                break;
            }

            OnDemandRequest r;

            synchronized (passiveRequests) {
                r = (OnDemandRequest) passiveRequests.pop();
            }

            while (r != null) {
                if (filePriorities[r.archive][r.file] != 0) {
                    filePriorities[r.archive][r.file] = 0;
                    sentRequests.pushBack(r);
                    sendRequest(r);
                    retrieving = true;

                    if (extrasLoaded < extrasCount) {
                        extrasLoaded++;
                    }

                    message = "Loading extra files - " + extrasLoaded + "/" + extrasCount;
                    passiveRequestsSent++;
                    if (passiveRequestsSent == 10) {
                        return;
                    }
                }
                synchronized (passiveRequests) {
                    r = (OnDemandRequest) passiveRequests.pop();
                }
            }

            for (int archive = 0; archive < 4; archive++) {
                byte priorities[] = this.filePriorities[archive];
                int count = priorities.length;

                for (int i = 0; i < count; i++) {
                    if (priorities[i] == highestPriority) {
                        priorities[i] = 0;
                        OnDemandRequest r1 = new OnDemandRequest();
                        r1.archive = archive;
                        r1.file = i;
                        r1.immediate = false;
                        sentRequests.pushBack(r1);
                        sendRequest(r1);
                        retrieving = true;

                        if (extrasLoaded < extrasCount) {
                            extrasLoaded++;
                        }

                        message = "Loading extra files - " + extrasLoaded + "/" + extrasCount;
                        passiveRequestsSent++;

                        if (passiveRequestsSent == 10) {
                            return;
                        }
                    }
                }

            }

            highestPriority--;
        }
    }

    public void remainingRequest() {
        immediateRequestsSent = 0;
        passiveRequestsSent = 0;

        for (OnDemandRequest request = (OnDemandRequest) sentRequests.top(); request != null; request = (OnDemandRequest) sentRequests.next()) {
            if (request.immediate) {
                immediateRequestsSent++;
            } else {
                passiveRequestsSent++;
            }
        }

        while (immediateRequestsSent < 10) {
            OnDemandRequest request = (OnDemandRequest) toRequest.pop();

            if (request == null) {
                break;
            }

            if (filePriorities[request.archive][request.file] != 0) {
                extrasLoaded++;
            }

            filePriorities[request.archive][request.file] = 0;
            sentRequests.pushBack(request);
            immediateRequestsSent++;
            sendRequest(request);
            retrieving = true;
        }
    }

    public void request(int file, int archive) {
        if (Game.cache[0] == null) {
            return;
        }
        if (fileVersions[archive][file] == 0) {
            return;
        }
        if (filePriorities[archive][file] == 0) {
            return;
        }
        if (highestPriority == 0) {
            return;
        }
        OnDemandRequest request = new OnDemandRequest();
        request.archive = archive;
        request.file = file;
        request.immediate = false;
        synchronized (passiveRequests) {
            passiveRequests.pushBack(request);
        }
    }

    public void requestModel(int i) {
        sendRequest(0, i);
    }

    public void requestRegions(boolean members) {
        int length = mapIndices.length;
        for (int i = 0; i < length; i++) {
            if (members || membersArea[i] != 0) {
                verify((byte) 2, 3, landscapeFiles[i]);
                verify((byte) 2, 3, mapFiles[i]);
            }
        }

    }

    public void run() {
        try {
            while (running) {
                cycle++;

                try {
                    if (highestPriority == 0 && Game.cache[0] != null) {
                        Thread.sleep(50);
                    } else {
                        Thread.sleep(20);
                    }
                } catch (Exception e) {
                }

                retrieving = true;

                for (int i = 0; i < 100; i++) {
                    if (!retrieving) {
                        break;
                    }

                    retrieving = false;

                    localComplete();
                    remainingRequest();

                    if (immediateRequestsSent == 0 && i >= 5) {
                        break;
                    }

                    loadExtras();

                    if (in != null) {
                        handleResponse();
                    }
                }

                boolean idle = false;

                for (OnDemandRequest r = (OnDemandRequest) sentRequests.top(); r != null; r = (OnDemandRequest) sentRequests.next()) {
                    if (r.immediate) {
                        idle = true;
                        r.cycle++;

                        if (r.cycle > 50) {
                            r.cycle = 0;
                            sendRequest(r);
                        }
                    }
                }

                if (!idle) {
                    for (OnDemandRequest request = (OnDemandRequest) sentRequests.top(); request != null; request = (OnDemandRequest) sentRequests.next()) {
                        idle = true;
                        request.cycle++;
                        if (request.cycle > 50) {
                            request.cycle = 0;
                            sendRequest(request);
                        }
                    }

                }

                if (idle) {
                    idleCycles++;

                    if (idleCycles > 750) {
                        try {
                            socket.close();
                        } catch (Exception _ex) {
                            /* nada */
                        }

                        socket = null;
                        in = null;
                        out = null;
                        toRead = 0;
                    }
                } else {
                    idleCycles = 0;
                    message = "";
                }

                if (Game.loggedIn && socket != null && out != null && (highestPriority > 0 || Game.cache[0] == null)) {
                    deadTime++;
                    if (deadTime > 500) {
                        deadTime = 0;

                        buffer.position = 0;
                        buffer.writeByte(0);
                        buffer.writeByte(0);
                        buffer.writeByte(0);
                        buffer.writeByte(10);

                        try {
                            out.write(buffer.payload, 0, 4);
                        } catch (IOException _ex) {
                            idleCycles = 5000;
                            _ex.printStackTrace();
                        }
                    }
                }
            }
            return;
        } catch (Exception e) {
            Signlink.error("od_ex " + e.getMessage());
        }
    }

    public void sendRequest(int archive, int file) {
        if (archive < 0 || archive > fileVersions.length || file < 0 || file >= fileVersions[archive].length) {
            return;
        }

        if (fileVersions[archive][file] == 0) {
            return;
        }

        synchronized (immediate) {
            for (OnDemandRequest r = (OnDemandRequest) immediate.top(); r != null; r = (OnDemandRequest) immediate.next()) {
                if (r.archive == archive && r.file == file) {
                    return;
                }
            }

            OnDemandRequest r = new OnDemandRequest();

            r.archive = archive;
            r.file = file;
            r.immediate = true;

            synchronized (wanted) {
                wanted.pushBack(r);
            }

            immediate.push(r);
        }
    }

    public void sendRequest(OnDemandRequest node) {
        try {
            if (socket == null) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastSocketOpen < 4000L) {
                    return;
                }
                lastSocketOpen = currentTime;

                socket = Game.instance.getSocket(43594 + Game.portOffset);
                in = socket.getInputStream();
                out = socket.getOutputStream();

                out.write(15);

                for (int j = 0; j < 8; j++) {
                    in.read();
                }

                idleCycles = 0;
            }

            buffer.position = 0;
            buffer.writeByte(node.archive);
            buffer.writeShort(node.file);
            buffer.writeByte(node.immediate ? 2 : Game.loggedIn ? 0 : 1);

            out.write(buffer.payload, 0, 4);
            deadTime = 0;
            fails = -10000;
        } catch (IOException e) {
            try {
                socket.close();
            } catch (Exception _ex) {
            }
            socket = null;
            in = null;
            out = null;
            toRead = 0;
            fails++;
            e.printStackTrace();
        }
    }

    public int sequenceFrameCount() {
        return animIndices.length;
    }

    public void setup(Archive archive) {
        for (int i = 0; i < VERSION_FILES.length; i++) {
            Buffer buffer = new Buffer(archive.get(VERSION_FILES[i]));
            int count = buffer.payload.length / 2;

            fileVersions[i] = new int[count];
            filePriorities[i] = new byte[count];

            for (int j = 0; j < count; j++) {
                fileVersions[i][j] = buffer.readUnsignedShort();
            }
        }

        for (int i = 0; i < CRC_FILES.length; i++) {
            Buffer buffer = new Buffer(archive.get(CRC_FILES[i]));

            crcs[i] = new int[buffer.payload.length / 4];

            for (int j = 0; j < crcs[i].length; j++) {
                crcs[i][j] = buffer.readInt();
            }
        }

        byte[] data = null;
        int size = -1;

        // Model
        {
            data = archive.get("model_index");
            size = fileVersions[0].length;

            modelIndices = new byte[size];

            for (int i = 0; i < size; i++) {
                if (i < data.length) {
                    modelIndices[i] = data[i];
                } else {
                    modelIndices[i] = 0x0;
                }
            }
        }

        // Map
        {
            Buffer b = new Buffer(archive.get("map_index"));
            size = b.payload.length / 7;

            mapIndices = new int[size];
            mapFiles = new int[size];
            landscapeFiles = new int[size];
            membersArea = new byte[size];

            for (int i = 0; i < size; i++) {
                mapIndices[i] = b.readUnsignedShort();
                mapFiles[i] = b.readUnsignedShort();
                landscapeFiles[i] = b.readUnsignedShort();
                membersArea[i] = b.readByte();
            }
        }

        // Animation
        {
            Buffer b = new Buffer(archive.get("anim_index"));
            size = b.payload.length / 2;

            animIndices = new int[size];

            for (int i = 0; i < size; i++) {
                animIndices[i] = b.readUnsignedShort();
            }
        }

        // Midi
        {
            Buffer b = new Buffer(archive.get("midi_index"));
            size = b.payload.length;

            midiIndices = new int[size];

            for (int i = 0; i < size; i++) {
                midiIndices[i] = b.readUnsignedByte();
            }
        }

        this.running = true;
        Game.instance.startThread(this, 2);
    }

    public void stop() {
        this.running = false;
    }

    public void verify(byte priority, int archive, int file) {
        if (Game.cache[0] == null) {
            return;
        }

        if (fileVersions[archive][file] == 0) {
            return;
        }

        if (data(fileVersions[archive][file], crcs[archive][file], Game.cache[archive + 1].get(file))) {
            return;
        }

        filePriorities[archive][file] = priority;

        if (priority > highestPriority) {
            highestPriority = priority;
        }

        extrasCount++;
    }
}
